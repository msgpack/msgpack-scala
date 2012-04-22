package org.msgpack.template.builder

import java.lang.reflect.Type
import org.msgpack.scalautil.ScalaSigUtil
import org.msgpack.template.{EnumerationTemplate, Template, TemplateRegistry}
import org.msgpack.template.TemplateRegistry._
import org.msgpack.MessageTypeException

/**
 *
 * User: takeshita
 * Create: 12/04/22 22:59
 */

class ScalaEnumTemplateBuilder(registry : TemplateRegistry) extends AbstractTemplateBuilder(registry) {

  def buildTemplate[T](targetClass: Class[T], entries: Array[FieldEntry]): Template[T] = {


    ScalaSigUtil.reverseCompanionObjectClass(targetClass) match{
      case Some(c) => {
        new EnumerationTemplate(c.asInstanceOf[Class[Enumeration]]).asInstanceOf[Template[T]]

      }
      case _ => throw new MessageTypeException("Not scala enumeration class")
    }

  }

  def matchType(targetType: Type, forceBuild: Boolean): Boolean = {
    targetType match{
      case com : Class[_] if classOf[Enumeration].isAssignableFrom(com) => {
        true
      }
      case _ => false
    }
  }
}
