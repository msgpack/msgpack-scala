package org.msgpack.template.builder

import org.msgpack.unpacker.Unpacker
import org.msgpack.packer.Packer
import org.msgpack.template.{AbstractTemplate, TemplateRegistry, Template}
import org.msgpack.MessageTypeException
import java.lang.Class
import java.lang.reflect.{Method, Type}

/**
 * 
 * User: takeshita
 * Create: 11/10/13 11:24
 */

class ReflectionScalaTemplateBuilder(registry : TemplateRegistry)
  extends ReflectionTemplateBuilder(registry) with ScalaObjectMatcher with ScalaPropertyFinder{

  override def buildTemplate[T](targetClass: Class[T], entries: Array[FieldEntry]) = {
    if(entries == null){
      throw new NullPointerException("entries is null: " + targetClass)
    }
    val templates = toScalaTemplates(entries)
    new ReflectionScalaTemplate[AnyRef](targetClass.asInstanceOf[Class[AnyRef]],templates).asInstanceOf[Template[T]]
  }

  private def toScalaTemplates( entries : Array[FieldEntry]) = {
    val templates : Array[ReflectionScalaFieldTemplate[_]] = new Array(entries.length)
    var i = 0
    for( e <- entries){
      if(e.isAvailable){
        val template = registry.lookup(e.getGenericType).asInstanceOf[Template[AnyRef]]
        templates(i) = new ReflectionScalaFieldTemplate(e.asInstanceOf[ScalaFieldEntry],template)
      }else{
        templates(i) = null
      }
      i += 1
    }
    templates
  }
}

/**
 * Store companion object
 */
object CompanionObjectMap{

  type CompanionObject = { def apply() : Any}

  var companions = Map[Class[_],CompanionObject]()

  def newInstance(clazz : Class[_]) = companions.synchronized{
    if(companions.contains(clazz)){
      companions(clazz).apply()
    }else{
      try{
        clazz.newInstance
      }catch{
        case e : InstantiationException =>{
          val c = registerCompanionObject(clazz)
          c.apply()
        }
      }
    }
  }

  def registerCompanionObject(clazz : Class[_]) : CompanionObject = {
    try{
      val companion = clazz.getClassLoader.loadClass(clazz.getName + "$").getDeclaredField("MODULE$").get(null).asInstanceOf[CompanionObject]
      companions +=( clazz -> companion)
      companion
    }catch{
      case e : ClassNotFoundException => {
        throw new MessageTypeException("Can't find plain constructor or companion object")
      }
      case e : NoSuchFieldException => {
        throw new MessageTypeException("Can't find plain constructor or companion object")
      }
    }
  }

}

class ReflectionScalaTemplate[T <: AnyRef](var targetClass : Class[T],
                                           var templates : Array[ReflectionScalaFieldTemplate[_]]) extends AbstractTemplate[T]{
  def read(unpacker: Unpacker, base: T, required: Boolean) : T = {
    if (!required && unpacker.trySkipNil) {
      return null.asInstanceOf[T]
    }
    val to : T = if (base == null) {
      CompanionObjectMap.newInstance(targetClass).asInstanceOf[T]
    }else base
    unpacker.readArrayBegin
    var i: Int = 0
    while (i < templates.length) {
      {
        var tmpl = templates(i)
        if (!tmpl.entry.isAvailable) {
          unpacker.skip
        }
        else if (tmpl.entry.isOptional && unpacker.trySkipNil) {
        }
        else {
          tmpl.asInstanceOf[Template[T]].read(unpacker, to, false)
        }
      }
      ({
        i += 1; i
      })
    }
    unpacker.readArrayEnd
    return to
  }


  def write(packer: Packer, target: T, required: Boolean) : Unit = {
    if (target == null) {
      if (required) {
        throw new MessageTypeException("attempted to write null")
      }
      packer.writeNil
      return
    }

    packer.writeArrayBegin(templates.length)
    for(template <- templates){
      if(!template.entry.isAvailable){
        packer.writeNil
      }else{
        val obj = template.entry.get(target).asInstanceOf[T]
        if(obj == null){
          if(template.entry.isNotNullable){
            throw new MessageTypeException(template.entry.getName + " cannot be null by @NotNullable")
          }
          packer.writeNil()
        }else{
          template.asInstanceOf[Template[T]].write(packer,obj,true)
        }
      }
    }
    packer.writeArrayEnd

  }
}

class ReflectionScalaFieldTemplate[T <: AnyRef](val entry : ScalaFieldEntry, template : Template[T]) extends AbstractTemplate[T]{
  def read(u: Unpacker, to: T, required: Boolean) : T = {
    val f = entry.get(to).asInstanceOf[T]
    val v = template.read(u,f,required)
    if( v != f){
      entry.set(to,v)
    }
    return v.asInstanceOf[T]
  }

  def write(pk: Packer, v: T, required: Boolean) = {
    template.write(pk,v,required)
  }
}