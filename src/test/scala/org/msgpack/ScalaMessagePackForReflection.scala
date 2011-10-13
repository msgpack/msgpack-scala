package org.msgpack

import template.{ScalaTemplateBuilderChain, ScalaTemplateRegistry}

/**
 * 
 * User: takeshita
 * Create: 11/10/13 12:57
 */

object ScalaMessagePackForReflection extends ScalaMessagePackWrapper with ValueConversions{
  def messagePack = {
    new MessagePack(new ScalaTemplateRegistry(){
      override def createTemplateBuilderChain() = {
        new ScalaTemplateBuilderChain(this,true)
      }
    })
  }
}