package org.msgpack.template.builder

import java.lang.reflect.Type
import org.msgpack.annotation.{Message, MessagePackMessage}
import java.lang.annotation.Annotation
import java.lang.annotation.{ Annotation => JAnnotation}

/**
 * 
 * User: takeshita
 * Create: 11/10/13 11:48
 */

trait ScalaObjectMatcher{
  self : TemplateBuilder =>

  override def matchType(targetType: Type, hasAnnotation: Boolean) = {

    val c : Class[_] = targetType.asInstanceOf[Class[_]]
    (isAnnotated(c, classOf[MessagePackMessage]) || isAnnotated(c, classOf[Message]) ) &&
    classOf[ScalaObject].isAssignableFrom(c)
  }

  private def isAnnotated(targetType : Class[_], annotation : Class[_ <: JAnnotation]) = {
    targetType.getAnnotation(annotation) != null

  }
}