package com.argmaps.akka.redis
package akka
package specs

import com.argmaps.akka.redis.specs.matcher._
import com.argmaps.akka.redis.model.Generators._

import org.specs._
import org.specs.runner._

import specification.Context
import org.specs.util.Duration

import org.scalacheck._

import scala.collection.SortedSet

import se.scalablesolutions.akka.actor.{Actor,ActorRegistry,Agent}
import Actor._
import se.scalablesolutions.akka.dispatch.Futures._

import net.liftweb.common._
import net.liftweb.util.IoHelpers._
import net.liftweb.util.TimeHelpers._

import java.io.{File, FileFilter}

import model._

class InMemoryAkkaTestObjectService extends AkkaTestObjectService
  with InMemoryAkkaTestObjectStorageFactory

class AkkaTestObjectServiceSpecTest extends SpecificationWithJUnit  with ScalaCheck with BoxMatchers {
    
  val empty = new Context {
    before {
      AkkaTestObject.service = actorOf[InMemoryAkkaTestObjectService]
    }
    after {
      AkkaTestObject.stopService
    }
  }

  val full = new Context {
    before {
      AkkaTestObject.service = actorOf[InMemoryAkkaTestObjectService]
      (1 to 1000).foreach(i => genAkkaTestObject.sample.foreach(AkkaTestObject.set))
    }
    after {
      AkkaTestObject.stopService
    }
  }

  "test storage" ->- empty should {
    "have no test elements stored" in {
      AkkaTestObject.count must beFull.which(_ must_== 0)
    }
    "insert test" in {
      val counter = Agent(0)
      Prop.forAll{p: AkkaTestObject =>
        counter(_ + 1)
        AkkaTestObject.set(p)
        AkkaTestObject.get(p.id) must beFull.which{_ must_== p}
        true
      } must pass (display(workers->4, wrkSize->20))
      AkkaTestObject.count must beFull.which(_ must_== counter())
      counter.close
    }
  }
}
