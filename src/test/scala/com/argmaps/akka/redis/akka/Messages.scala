package com.argmaps.akka.redis
package akka

import model._

import se.scalablesolutions.akka.actor._

sealed trait Message

case object CountAkkaTestObjects extends Message

case object GetAkkaTestObjectIds extends Message

case class SetAkkaTestObject(akkaTestObject: AkkaTestObject) extends Message

case class GetAkkaTestObject(id: String) extends Message

case class GetAkkaTestObjects(ids: List[String]) extends Message

case class ReIndex(akkaTestObject: AkkaTestObject) extends Message

case class ForEachAkkaTestObject(fun: (AkkaTestObject) => Unit) extends Message

