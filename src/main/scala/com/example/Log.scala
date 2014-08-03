package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {

  def allRequests = scala.io.Source.fromFile(path).getLines.map(LogEntry(_).getRequest).toList

  private def unknownData(field: Field): Boolean = field != UnknownData

  private def groupByStatus(requests: Seq[Request]) =
    requests.map(_.resultStatus).filter(unknownData(_)).groupBy(_.get.head)

  private def groupByProperty(getProperty: Request => Field): List[(String, Int)] =
    allRequests.filter(req => unknownData(getProperty(req))).map(getProperty(_).get).groupBy(identity).toList.map { case (property, list) => (property, list.length)}.sortBy(_._2)

  private def numberOfClientErrors(requests: Seq[Request]): Int = groupByStatus(requests).get('4').getOrElse(Seq()).length

  private def numberOfServerErrors(requests: Seq[Request]): Int = groupByStatus(requests).get('5').getOrElse(Seq()).length

  private def numberOfErrorStatus(requests: Seq[Request]) = numberOfClientErrors(requests) + numberOfServerErrors(requests)

  def serverErrorRate(requests: Seq[Request]) = numberOfServerErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def clientErrorRate(requests: Seq[Request]) = numberOfClientErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def numberOfRequests(requests: Seq[Request]) = requests.length

  def sumSizeOfResponses(requests: Seq[Request]) = requests.map(_.resultSize.get.toInt).filter(_ != None).sum

  def errorRate(requests: Seq[Request]) = numberOfErrorStatus(requests).toDouble / numberOfRequests(requests).toDouble

  def topTenURLs = groupByProperty(req => req.clientRequest).takeRight(10).reverse

  def mostCommonIp = List(groupByProperty(req => req.host).last)

  def mostErroneousPath = List(groupByProperty(req => req.clientRequest).last)

  def statistics(list: List[(String, Int)], requestField: Request => String) = {
    for {
      current <- list
      element = current._1
      reqCount = current._2
      elementRequests = allRequests.filter(requestField(_) == element)
      respSize = sumSizeOfResponses(elementRequests)
      elementErrorRate = errorRate(elementRequests)
    } yield s"""$element\t$reqCount\t$respSize\t$elementErrorRate"""
  } mkString ("\n")

  def erroneousPath = {
    for {
      clientRequest <- mostErroneousPath
      path = clientRequest._1
      requestsWithPath = allRequests.filter(req => unknownData(req.clientRequest) && req.clientRequest.get == path)
      clientErrors = numberOfClientErrors(requestsWithPath)
      serverErrors = numberOfServerErrors(requestsWithPath)
    } yield s"""$path: client errors: $clientErrors, server errors: $serverErrors"""
  } mkString ("\n")

}
