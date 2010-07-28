package com.argmaps.akka.redis.model

import org.scalacheck._

import java.util.Date
import java.util.{Calendar => C}
import java.text.SimpleDateFormat

import Gen._
import Arbitrary.arbitrary

object Generators {

  val isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  val idDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss00")

  def genAkkaTestObjectDate: Gen[List[Int]] = {
    val cal = C.getInstance
    cal.set(2000,1,1)
    val calstart = cal.getTimeInMillis
    cal.set(2010,1,1)
    val calend = cal.getTimeInMillis
    Gen.choose(calstart,calend).map{i =>
      cal.setTimeInMillis(i)
      cal.get(C.YEAR) :: (cal.get(C.MONTH) + 1) :: List(C.DATE, C.HOUR_OF_DAY, C.MINUTE, C.SECOND, C.MILLISECOND).map(cal.get)
    }
  }

  def genIdHash: Gen[String] = {
    Gen.choose(java.lang.Integer.MIN_VALUE, java.lang.Integer.MAX_VALUE).map(java.lang.Integer.toHexString)
  }

  def genAkkaTestObject: Gen[AkkaTestObject] = for {
    id <- genIdHash
    da <- genAkkaTestObjectDate
  } yield AkkaTestObject(AkkaTestObject.mkId(da,id), da)

  implicit def arbAkkaTestObject: Arbitrary[AkkaTestObject] = {
    Arbitrary { genAkkaTestObject }
  }
}
