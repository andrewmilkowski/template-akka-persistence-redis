package com.argmaps.akka.redis
package akka

import net.liftweb.common._
import Box._
import model._

import se.scalablesolutions.akka.actor._

trait AkkaTestObjectStorage extends Actor {
  type V = String
  type K = String

  def receive = {
    case CountAkkaTestObjects =>
      self.reply(size)

    case GetAkkaTestObjectIds =>
      self.reply(keys)

    case SetAkkaTestObject(akkaTestObject) =>
      setAkkaTestObject(akkaTestObject, AkkaTestObject.serialize(akkaTestObject))

    case GetAkkaTestObject(id) =>
      self.reply(getAkkaTestObject(id).map(AkkaTestObject.deserialize))

    case ForEachAkkaTestObject(f) =>
      foreach(v => f(AkkaTestObject.deserialize(v)))
  }

  def get(k: K): Option[V]

  def put(k: K, v: V): Unit

  def size: Int

  def keys: Iterable[K]

  def foreach(f: (V) => Unit): Unit

  def setAkkaTestObject(akkaTestObject: AkkaTestObject, v: V): Unit = put(akkaTestObject.id, v)

  def getAkkaTestObject(k: K): Option[V] = get(k)

}
