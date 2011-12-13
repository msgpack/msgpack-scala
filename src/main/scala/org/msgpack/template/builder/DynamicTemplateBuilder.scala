package org.msgpack.template.builder

import java.lang.reflect.Type
import java.lang.{Class, String}
import org.msgpack.template.{DynamicTemplate, TemplateRegistry, FieldList, Template}

/**
 * 
 * User: takeshita
 * Create: 11/12/13 14:02
 */

class DynamicTemplateBuilder(registry : TemplateRegistry) extends AbstractTemplateBuilder(registry) {

  def buildTemplate[T](targetClass: Class[T], entries: Array[FieldEntry]): Template[T] = {
    new DynamicTemplate(registry).asInstanceOf[Template[T]]
  }

  def matchType(targetType: Type, forceBuild: Boolean): Boolean = {
    targetType.equals(classOf[Object])
  }
}