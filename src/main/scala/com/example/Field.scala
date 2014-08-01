package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
class Field {
  val fields = Seq("Host", "Identifier", "UserId", "Date", "ClientRequest", "ResultStatus", "ResultSize", "Referrer", "UserAgent")

  def get(field: String, fieldNum: Int) = {
    if (field == "-") UnknownData
    else fieldNum match {
      case 1 => Host(field)
      case 2 => Identifier(field)
      case 3 => UserId(field)
      case 4 => Date(field)
      case 5 => ClientRequest(field)
      case 6 => ResultStatus(field.toInt)
      case 7 => ResultSize(field.toInt)
      case 8 => Referrer(field)
      case 9 => UserAgent(field)
    }
  }
}

case class Host(host: String) extends Field

case class Identifier(identifier: String) extends Field

case class UserId(userId: String) extends Field

case class Date(date: String) extends Field

case class ClientRequest(req: String) extends Field

case class ResultStatus(status: Int) extends Field

case class ResultSize(size: Int) extends Field

case class Referrer(ref: String) extends Field

case class UserAgent(agent: String) extends Field

object UnknownData extends Field

















