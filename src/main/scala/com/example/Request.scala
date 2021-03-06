package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Request(fields: String*) {
  val host = if (fields(0) == "-") UnknownData else Host(fields(0))
  val identifier = if (fields(1) == "-") UnknownData else Identifier(fields(1))
  val userId = if (fields(2) == "-") UnknownData else UserId(fields(2))
  val date = if (fields(3) == "-") UnknownData else Date(fields(3))
  val clientRequest = if (fields(4) == "-") UnknownData else ClientRequest(fields(4))
  val resultStatus = if (fields(5) == "-") UnknownData else ResultStatus(fields(5))
  val resultSize = if (fields(6) == "-") UnknownData else ResultSize(fields(6))
  val referrer = if (fields(7) == "-") UnknownData else Referrer(fields(7))
  val userAgent = if (fields(8) == "-") UnknownData else UserAgent(fields(8))
}
