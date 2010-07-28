package com.argmaps.akka.redis
package model

import net.liftweb.common._
import Box._
import net.liftweb.util.Helpers._
import net.liftweb.json._
import JsonAST._
import JsonDSL._
import JsonParser._
import Serialization.{read, write}

import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.dispatch._

import akka._

case class AkkaTestObject(id: String, createDate: List[Int]) {
  def uri = "/testakka/" + id
  def toJson = {
    ("id", id) ~
    ("uri", uri) ~
    ("createdate", AkkaTestObject.mkDate(createDate)) 
  }
}

object AkkaTestObject {

  private val noService = Failure("AkkaTestObject service not set")
  private var _service: Box[ActorRef] = noService

  def service = _service

  def service_=(ps: ActorRef): Unit = {
    _service = Box !! ps.start orElse noService
//    reIndex
  }

  def stopService: Unit = {
    _service.foreach(_.stop)
    _service = noService
  }


  def reIndex: Unit =
    logTime("ReIndexing akkaTestObject"){
      for {
        s <- service
        id <- ((s !! GetAkkaTestObjectIds) ?~ "Timed out").asA[List[String]].getOrElse(Nil)
        akkaTestObject <- get(id)
      } { s ! ReIndex(akkaTestObject) }
    }

  def count =
    for {
      s <- service
      res <- ((s !! CountAkkaTestObjects) ?~ "Timed out").asA[java.lang.Integer].map(_.intValue)
    } yield res

  def set(akkaTestObject: AkkaTestObject): Unit =
    for {
      s <- service
    } s ! SetAkkaTestObject(akkaTestObject)

  def get(id: String) =
    for {
      s <- service
      res <- ((s !! GetAkkaTestObject(id)) ?~ "Timed out" ~> 500).asA[Option[AkkaTestObject]] ?~ "Invalid Response"
      akkaTestObject <-res ?~ "AkkaTestObject Not Found" ~> 404
    } yield akkaTestObject

  def serialize(in: AkkaTestObject) = {
    implicit val formats = DefaultFormats 
    write(in)
  }

  def deserialize(in: String) = {
    implicit val formats = DefaultFormats 
    read[AkkaTestObject](in)
  }

  def mkId(date: List[Int], hash: String) = date match {
    case year :: month :: day :: hour :: minute :: second :: msecond :: rest =>
      "%04d%02d%02d-%02d%02d%02d%02d-%s".format(year, month, day, hour, minute, second, msecond, hash)
  }

  def mkDate(date: List[Int]) =
    "%04d-%02d-%02dT%02d:%02d:%02d.%02d" format (date:_*)

}

