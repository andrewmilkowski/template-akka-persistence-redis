package com.argmaps.akka.redis
package akka

trait RedisHelpers {
  protected implicit def intToByteArray(in: Int): Array[Byte] = stringToByteArray(in.toString)
  protected implicit def stringToByteArray(in: String): Array[Byte] = in.getBytes("UTF-8")
  protected implicit def asString(in: Array[Byte]): String = new String(in, "UTF-8")
}
