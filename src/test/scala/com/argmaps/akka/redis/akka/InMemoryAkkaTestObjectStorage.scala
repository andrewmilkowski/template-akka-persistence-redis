package com.argmaps.akka.redis
package akka

import net.liftweb.common._

import model._

import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.stm.transactional._
import se.scalablesolutions.akka.stm.local._
import se.scalablesolutions.akka.config.ScalaConfig._

trait InMemoryAkkaTestObjectStorageFactory {
  this: Actor =>
  val storage: ActorRef = this.self.spawnLink[InMemoryAkkaTestObjectStorage]
}

class InMemoryAkkaTestObjectStorage extends AkkaTestObjectStorage {
//  self.makeTransactionRequired
  
  self.lifeCycle = Some(LifeCycle(Permanent))

  val akkaTestObjects = TransactionalMap[K, V]

  def get(k: K): Option[V] = atomic { akkaTestObjects.get(k) }

  def put(k: K, v: V): Unit = atomic { akkaTestObjects.put(k, v) }

  def size: Int = atomic { akkaTestObjects.size }

  def keys: Iterable[K] = atomic { akkaTestObjects.keysIterator.toList }

  def foreach(f: (V) => Unit) = atomic { akkaTestObjects.valuesIterator.foreach(f) }

}

