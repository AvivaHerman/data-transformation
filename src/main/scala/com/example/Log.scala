package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {


  def allRequests = scala.io.Source.fromFile(path).getLines.map(LogEntry(_).getRequest).toList

  private def unknownData(field: Field): Boolean = field != UnknownData

  private def groupByStatus(requests: Seq[Request]) =
    requests.map(_.resultStatus).filter(unknownData(_)).groupBy(_.get.head)

  private def numberOfClientErrors(requests: Seq[Request]): Int = groupByStatus(requests).get('4').getOrElse(Seq()).length

  private def numberOfServerErrors(requests: Seq[Request]): Int = groupByStatus(requests).get('5').getOrElse(Seq()).length

  private def numberOfErrorStatus(requests: Seq[Request]) = numberOfClientErrors(requests) + numberOfServerErrors(requests)

  def serverErrorRate(requests: Seq[Request]) = numberOfServerErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def clientErrorRate(requests: Seq[Request]) = numberOfClientErrors(requests).toDouble / numberOfErrorStatus(requests).toDouble

  def numberOfRequests(requests: Seq[Request]) = requests.length

  def sumSizeOfResponses(requests: Seq[Request]) = requests.map(_.resultSize.get.toInt).filter(_ != None).sum

  def errorRate(requests: Seq[Request]) = numberOfErrorStatus(requests).toDouble / numberOfRequests(requests).toDouble

  def topTenURLs = allRequests.filter(req => unknownData(req.clientRequest)).map(_.clientRequest.get).groupBy(identity).toList.map{ case (url, list) => (url, list.length) }.sortBy(_._2).takeRight(10).reverse

  def statisticsOfTopTen = {
    for {
      currentUrl <- topTenURLs
      url = currentUrl._1
      reqCount = currentUrl._2
      urlRequests = allRequests.filter(_.clientRequest.get == url)
      respSize = sumSizeOfResponses(urlRequests)
      urlErrorRate = errorRate(urlRequests)
    } yield s"""$url\t$reqCount\t$respSize\t$urlErrorRate"""
  } mkString("\n")


}
