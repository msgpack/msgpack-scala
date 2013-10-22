package org.msgpack.scalautil

import java.lang.reflect.{Field, Method, Type => JType, ParameterizedType}
import tools.scalap.scalax.rules.scalasig._
import com.sun.xml.internal.ws.api.PropertySet

/**
 *
 * User: takeshita
 * Create: 12/04/03 1:22
 */

object ScalaSigUtil {


  val SetterSuffix = "_$eq"

  def getAllPropGetters(clazz : Class[_]) : Seq[(String,MethodSymbol)] = {

    def superClassProps = {
      val superClass = clazz.getSuperclass
      if(superClass == null || superClass == classOf[java.lang.Object]){
        Nil
      }else{
        getAllPropGetters(superClass)
      }
    }

    def interfaceProps = {
      clazz.getInterfaces.foldLeft(Seq[(String,MethodSymbol)]())( (l,i) => l ++ getAllPropGetters(i))
    }

    superClassProps ++ interfaceProps ++ getPropGetters(clazz)


  }

  def getPropGetters(clazz : Class[_]) : Seq[(String,MethodSymbol)] = {

    val sig = ScalaSigParser.parse(clazz)

    if(sig.isEmpty){
      return Nil
    }

    val (setters,getters) = sig.get.symbols.collect({
      case m : MethodSymbol => {
        m.name -> m
      }
    }).partition(v => v._1.endsWith(SetterSuffix))
    val getterMap = getters.toMap
    setters.map(s => s._1.substring(0,s._1.length - 4)).
      filter(fieldName => getterMap.contains(fieldName)).map(fieldName => {
      fieldName -> getterMap(fieldName)
    })
  }


  def getReturnType(methodSymbol : MethodSymbol) : Option[JType] = {
    methodSymbol.infoType match{
      case NullaryMethodType(returnType) => toJavaClass(returnType)
      case MethodType(returnType,methodParams) => toJavaClass(returnType)
      case trt : TypeRefType => toJavaClass(trt)
      case _ => {
        None
      }
    }
  }

  def toJavaClass( t : Type , primitive_? : Boolean = true) : Option[JType] = t match{
    case TypeRefType(prefix,clazz , genericParams) => {
      val nameMapper = if(primitive_?) mapToPrimitiveJavaName else mapToRefJavaName
      if(clazz.path == "scala.Array"){
        toJavaClass(genericParams(0),true) match{
          case Some(c : Class[_]) => if(c.isPrimitive) Some(forName("[" + c.getName.toUpperCase.charAt(0))) else Some(forName("[L" + c.getName + ";"))
          case Some(c : ParameterizedType) => Some(forName("[L" + c.getRawType + ";"))
          case _ => throw new Exception("Never match here")
        }
      }else if(clazz.path == "scala.Enumeration.Value"){
        prefix match{
          case SingleType(_,name) => {
            Some(nameMapper(name.path))
          }
        }
      }else if(genericParams.size == 0){
        Some(nameMapper(clazz.path))
      }else{
        Some(new MyParameterizedType(
          nameMapper(clazz.path),
          genericParams.map(p => toJavaClass(p,false).get).toArray))
      }
    }
    case _ => {
      None
    }

  }
  def forName(name : String) = Class.forName(name)

  trait MapToJavaName extends Function1[String, Class[_]]{
    val nameMap : Map[String,Class[_]]
    def apply(scalaClassName : String) : Class[_] = {
      if(scalaClassName.startsWith("scala")){
        nameMap.getOrElse(scalaClassName,forName(scalaClassName))
      }else{
        if(scalaClassName.startsWith("<empty>.")) forName(scalaClassName.substring(8))
        else forName(scalaClassName)
      }
    }
  }


  val commonNameMaps : Seq[(String, Class[_])] = Seq(
    "scala.Predef.String" -> classOf[java.lang.String],
    "scala.Predef.Map" -> classOf[Map[_,_]],
    "scala.Predef.Seq" -> classOf[Seq[_]],
    "scala.Predef.Set" -> classOf[Set[_]],
    "scala.package.List" -> classOf[List[_]],
    "scala.Unit" ->classOf[java.lang.Void],
    "scala.package.Seq" -> classOf[Seq[_]],
    "scala.package.Either" -> classOf[Either[_,_]]
  )

  object mapToRefJavaName extends MapToJavaName{
    val nameMap =  commonNameMaps ++ Seq(
      "scala.Int" -> classOf[java.lang.Integer],
      "scala.Byte" -> classOf[java.lang.Byte],
      "scala.Short" -> classOf[java.lang.Short],
      "scala.Long" -> classOf[java.lang.Long],
      "scala.Float" -> classOf[java.lang.Float],
      "scala.Double" -> classOf[java.lang.Double],
      "scala.Boolean" -> classOf[java.lang.Boolean]
    ) toMap
  }

  object mapToPrimitiveJavaName extends MapToJavaName{
    val nameMap =  commonNameMaps ++ Seq(
      "scala.Int" -> java.lang.Integer.TYPE,
      "scala.Byte" -> java.lang.Byte.TYPE,
      "scala.Short" -> java.lang.Short.TYPE,
      "scala.Long" -> java.lang.Long.TYPE,
      "scala.Float" -> java.lang.Float.TYPE,
      "scala.Double" -> java.lang.Double.TYPE,
      "scala.Boolean" -> java.lang.Boolean.TYPE
    ) toMap
  }

  def getCompanionObjectClass(clazz:  Class[_]) : Option[Class[_]] = {
    if(clazz.getName.endsWith("$")) None
    else{
      try{
        val c = Class.forName(clazz.getName + "$")
        Some(c)
      }catch{
        case e : NoClassDefFoundError => {
          None
        }
        case e : ClassNotFoundException =>
        {
          None
        }
      }
    }
  }
  def reverseCompanionObjectClass(clazz : Class[_]) : Option[Class[_]] = {
    if(!clazz.getName.endsWith("$")) None
    else{
      try{
        val c = Class.forName(clazz.getName.substring(0,clazz.getName.length - 1))
        Some(c)
      }catch{
        case e : NoClassDefFoundError => {
          None
        }
        case e : ClassNotFoundException =>
        {
          None
        }
      }
    }
  }

}


class MyParameterizedType(rowClass : Class[_],paramClasses : Array[JType]) extends ParameterizedType{
  def getActualTypeArguments: Array[JType] = paramClasses

  def getRawType: JType = rowClass

  def getOwnerType: JType = null

  override def toString: String = {
    rowClass.toString + "<" + paramClasses.mkString(",") + ">"
  }
}

object MyParameterizedType{
  def apply(m : Manifest[_]) : MyParameterizedType = {
    new MyParameterizedType(m.erasure,m.typeArguments.map(MyParameterizedType.apply(_)).toArray)

  }
}
