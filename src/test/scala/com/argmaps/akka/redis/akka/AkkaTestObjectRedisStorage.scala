package com.argmaps.akka.redis
package akka

import net.liftweb.common._

import model._

import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.stm.transactional._
import se.scalablesolutions.akka.stm.local._
import se.scalablesolutions.akka.persistence.redis.RedisStorage
import se.scalablesolutions.akka.config.ScalaConfig._

trait AkkaTestObjectRedisStorageFactory {
  this: AkkaTestObjectService =>
  val storage: ActorRef = this.self.spawnLink[AkkaTestObjectRedisStorage]
}

class AkkaTestObjectRedisStorage extends AkkaTestObjectStorage with RedisHelpers {

  self.lifeCycle = Some(LifeCycle(Permanent))

  var akkaTestObjects = RedisStorage.getMap("akkaTestObjects")

  def get(k: K): Option[V] = atomic { akkaTestObjects.get(k).map(asString) }

  def put(k: K, v: V): Unit = atomic { akkaTestObjects.put(k, v) }

  def size: Int = atomic { akkaTestObjects.size }

  def keys: Iterable[K] = atomic { akkaTestObjects.keysIterator.map(asString).toList }

  def foreach(f: (V) => Unit) = atomic { akkaTestObjects.valuesIterator.map(asString).foreach(f) }

  override def postRestart(reason: Throwable) =
    akkaTestObjects = RedisStorage.getMap("akkaTestObjects")

}

