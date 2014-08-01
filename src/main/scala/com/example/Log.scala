package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {

  def requests = scala.io.Source.fromFile(path).getLines map (LogEntry(_).getRequest)

  def groupByStatus =
    requests.map(_.resultStatus).filter(status => status != UnknownData).toList.groupBy(_.get.head)

  private def errorStatus = groupByStatus.get('4').getOrElse(Seq()).length + groupByStatus.get('5').getOrElse(Seq()).length

  def numberOfRequests = requests.length

  def sumSizeOfResponses = requests.map(_.resultSize.get.toInt).filter(_ != None).sum

  def errorRate = errorStatus.toDouble / numberOfRequests.toDouble

}
