package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {

  def allRequests = scala.io.Source.fromFile(path).getLines.map(LogEntry(_).getRequest).toList

  private def notUnknownData[T](field: T): Boolean = field != UnknownData

  private def groupByStatus(requests: Seq[Request]) = groupByProperty(requests, req => req.resultStatus).toMap

  private def groupByProperty[T](requests: Seq[Request], getProperty: Request => T): Seq[(String, Seq[Request])] =
    requests
      .filter(req => notUnknownData(getProperty(req)))
      .groupBy {
      getProperty(_) match {
        case Host(ip) => ip
        case Identifier(id) => id
        case Date(date) => date.split(":").init.mkString(":")
        case ClientRequest(req) => req.split(" ")(1)
        case ResultStatus(status) => status.head.toString
        case ResultSize(size) => size
        case UserId(id) => id
        case UserAgent(agent) => agent
        case Referrer(ref) => ref
      }
    }
      .toList
      .sortBy(_._2.length)

  private def numberOfClientErrors(requests: Seq[Request]): Int = groupByStatus(requests).get("4").getOrElse(Seq()).length

  private def numberOfServerErrors(requests: Seq[Request]): Int = groupByStatus(requests).get("5").getOrElse(Seq()).length

  private def numberOfErrorStatus(requests: Seq[Request]) = numberOfClientErrors(requests) + numberOfServerErrors(requests)

  def serverErrorRate(requests: Seq[Request]) = numberOfServerErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def clientErrorRate(requests: Seq[Request]) = numberOfClientErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def numberOfRequests(requests: Seq[Request]) = requests.length

  def sumSizeOfResponses(requests: Seq[Request]) =
    requests
      .filter(req => notUnknownData(req.resultSize))
      .map {
      _.resultSize match {
        case ResultSize(size) => size.toInt
      }
    }
      .sum

  def errorRate(requests: Seq[Request]) = numberOfErrorStatus(requests).toDouble / numberOfRequests(requests).toDouble

  def topTenURLs = groupByProperty(allRequests, req => req.clientRequest).takeRight(10).reverse

  def mostCommonIp = List(groupByProperty(allRequests, req => req.host).last)

  def mostErroneousPath = groupByProperty(allRequests, req => req.clientRequest)
    .map { case (path, list) => (path, numberOfErrorStatus(list), numberOfClientErrors(list), numberOfServerErrors(list))}
    .maxBy(_._2) match {
    case (path, _, clientErrors, serverErrors) => s"""$path: client errors: $clientErrors, server errors: $serverErrors"""
  }


  def statistics(list: Seq[(String, Seq[Request])]) = {
    for {
      current <- list
      element = current._1
      reqCount = current._2.length
      elementRequests = current._2
      respSize = sumSizeOfResponses(elementRequests)
      elementErrorRate = errorRate(elementRequests)
    } yield s"""$element\t$reqCount\t$respSize\t$elementErrorRate"""
  }
    .mkString("\n")

  def groupByHour = groupByProperty(allRequests, req => req.date)

}
