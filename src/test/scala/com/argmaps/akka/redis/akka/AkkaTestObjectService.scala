package com.argmaps.akka.redis
package akka

import collection.immutable.{TreeMap,TreeSet}

import net.liftweb.common._
import Box._
import net.liftweb.util.Helpers._

import model._

import se.scalablesolutions.akka.actor._
import Actor._
import se.scalablesolutions.akka.stm.global._
import se.scalablesolutions.akka.dispatch._
import Futures._
import se.scalablesolutions.akka.config.ScalaConfig._
import se.scalablesolutions.akka.config._

import net.liftweb.json._

class AkkaTestObjectRedisService extends AkkaTestObjectService with AkkaTestObjectRedisStorageFactory

abstract class AkkaTestObjectService extends Actor with Logger {
  self.faultHandler = Some(AllForOneStrategy(5, 5000))
  self.trapExit = List(classOf[Exception])

  val storage: ActorRef

  def receive = {
    case CountAkkaTestObjects => storage forward CountAkkaTestObjects
    case msg: ForEachAkkaTestObject => storage forward msg
    case GetAkkaTestObjectIds => storage forward GetAkkaTestObjectIds
    case msg: SetAkkaTestObject => storage ! msg
    case msg: GetAkkaTestObject => storage forward msg
    case msg: GetAkkaTestObjects => storage forward msg
  }

  override def shutdown = self.shutdownLinkedActors

}
