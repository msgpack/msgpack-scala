package org.msgpack

import template._
import builder._
import collection.mutable._

/**
 * 
 * User: takeshita
 * Create: 11/10/12 21:21
 */

class ScalaTemplateRegistry extends TemplateRegistry(null){



  {
    def anyTemplate[T] = AnyTemplate.getInstance(this).asInstanceOf[Template[T]]

    register(new ImmutableListTemplate[Any](anyTemplate))
    register(new ImmutableMapTemplate(anyTemplate,anyTemplate))
    register(new DoubleLinkedListTemplate(anyTemplate))
    register(new LinkedListTemplate(anyTemplate))
    register(new ListBufferTemplate(anyTemplate))
    register(new MutableListCTemplate(anyTemplate))
    register(new MutableHashMapTemplate(anyTemplate,anyTemplate))
    register(new MutableListMapTemplate(anyTemplate,anyTemplate))
    register(new MutableLinkedHashMapTemplate(anyTemplate,anyTemplate))
    register(classOf[scala.collection.mutable.Map[_,_]],
    new MutableHashMapTemplate(anyTemplate,anyTemplate))
    register(classOf[scala.collection.mutable.Seq[_]],
      new LinkedListTemplate(anyTemplate))
    register(classOf[Seq[_]],new ImmutableListTemplate(anyTemplate))

    // generics
    registerGeneric(classOf[scala.collection.immutable.List[_]],new GenericImmutableListTemplate())
    registerGeneric(classOf[scala.collection.immutable.Map[_,_]],new GenericImmutableMapTemplate())
    registerGeneric(classOf[scala.collection.immutable.Seq[_]],new GenericImmutableListTemplate())

    registerGeneric(classOf[DoubleLinkedList[_]],
      new GenericMutableListTemplate[DoubleLinkedListTemplate[_]]())
    registerGeneric(classOf[ListBuffer[_]],
      new GenericMutableListTemplate[ListBufferTemplate[_]]())
    registerGeneric(classOf[MutableList[_]],
      new GenericMutableListTemplate[MutableListCTemplate[_]]())
    registerGeneric(classOf[LinkedList[_]],
      new GenericMutableListTemplate[LinkedListTemplate[_]]())
    registerGeneric(classOf[scala.collection.mutable.Seq[_]],
      new GenericMutableListTemplate[LinkedListTemplate[_]]())

    registerGeneric(classOf[LinkedHashMap[_,_]],
      new GenericMutableMapTemplate[MutableLinkedHashMapTemplate[_,_]])
    registerGeneric(classOf[HashMap[_,_]],
      new GenericMutableMapTemplate[MutableHashMapTemplate[_,_]])
    registerGeneric(classOf[ListMap[_,_]],
      new GenericMutableMapTemplate[MutableListMapTemplate[_,_]])
    registerGeneric(classOf[scala.collection.mutable.Map[_,_]],
      new GenericMutableMapTemplate[MutableHashMapTemplate[_,_]])

  }

  def register[T]( template : Template[T])(implicit manifest : Manifest[T]) : Unit = {
    this.register(manifest.erasure,template)
  }


  override def createTemplateBuilderChain() = {
    new ScalaTemplateBuilderChain(this)
  }
}

class ScalaTemplateBuilderChain(registry : TemplateRegistry) extends TemplateBuilderChain(registry){

  private def enableDynamicCodeGeneration: Boolean = {
    try {
      return !System.getProperty("java.vm.name").equals("Dalvik")
    }
    catch {
      case e: Exception => {
        return true
      }
    }
  }

  override def reset(registry: TemplateRegistry, cl: ClassLoader) = {
    if (registry == null) {
      throw new NullPointerException("registry is null")
    }

    templateBuilders.add(new ArrayTemplateBuilder(registry))


    templateBuilders.add(new OrdinalEnumTemplateBuilder(registry))
    if (enableDynamicCodeGeneration) {
      val b = new JavassistScalaTemplateBuilder(registry)
      if(cl != null){
        b.addClassLoader(cl)
      }
      templateBuilders.add(b)
      val builder = new JavassistTemplateBuilder(registry)
      if (cl != null) {
        builder.addClassLoader(cl)
      }
      templateBuilders.add(builder)
      templateBuilders.add(new JavassistBeansTemplateBuilder(registry))
    }
    else {
      val builder = new ReflectionTemplateBuilder(registry)
      templateBuilders.add(builder)
      templateBuilders.add(new OrdinalEnumTemplateBuilder(registry))
      templateBuilders.add(new ReflectionBeansTemplateBuilder(registry))
    }

  }
}