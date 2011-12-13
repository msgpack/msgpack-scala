package org.msgpack.template

import org.msgpack.packer.Packer
import org.msgpack.unpacker.Unpacker
import java.util.Calendar
import org.msgpack.`type`.ValueType
import org.msgpack.MessageTypeException

/*
 * Template for Calendar.
 * This template convert Calendar to long which is millisec from epoch
 * User: takeshita
 * Date: 11/03/11
 * Time: 11:11
 */
class DynamicTemplate(registry : TemplateRegistry) extends AbstractTemplate[AnyRef]{


  def write(packer: Packer, v: AnyRef, required: Boolean) : Unit = {

    if(v == null){
      if(required){
        throw new MessageTypeException("Attempted to write null")
      }
      packer.writeNil()
    }else{
      val tmpl = getTemplate(v.getClass)

      tmpl.write(packer,v,required)
    }
  }

  def read(u: Unpacker, to: AnyRef, required: Boolean): AnyRef = {

    if(!required && u.trySkipNil){
      return null
    }else{
      if(to != null){
        val tmpl = getTemplate(to.getClass)
        tmpl.read(u,to,required)
      }else{
        val v = u.readValue()
        v.getType match{
          case ValueType.BOOLEAN => Boolean.box(v.asBooleanValue().getBoolean )
          case ValueType.INTEGER => Long.box(v.asIntegerValue().getLong())
          case ValueType.RAW => v.asRawValue().getString
          case ValueType.FLOAT => Double.box(v.asFloatValue().getDouble)
          case valueType => {
            throw new MessageTypeException("Not primitive type:%s".format(valueType))
          }
        }


      }
    }

  }

  def getTemplate(clazz : Class[_]) : Template[AnyRef] = {

    val tmpl = registry.lookup(clazz)
    if(tmpl == null){
      throw new MessageTypeException("Can't find for class:" + clazz)
    }
    if(classOf[DynamicTemplate].isAssignableFrom(tmpl.getClass)){
      throw new MessageTypeException("Can't find for class:" + clazz)
    }
    tmpl.asInstanceOf[Template[AnyRef]]
  }
}