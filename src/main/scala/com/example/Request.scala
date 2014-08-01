package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Request(fields: Field*) {
  val host = fields(0)
  val identifier = fields(1)
  val userId = fields(2)
  val date = fields(3)
  val clientRequest = fields(4)
  val resultStatus = fields(5)
  val resultSize = fields(6)
  val referrer = fields(7)
  val userAgent = fields(8)
}
