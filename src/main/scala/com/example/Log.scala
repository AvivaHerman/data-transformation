package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {


  private def requests = scala.io.Source.fromFile(path).getLines map (LogEntry(_).getRequest)

  private def groupByStatus =
    requests.map(_.resultStatus).filter(status => status != UnknownData).toList.groupBy(_.get.head)

  private def numberOfClientErrors: Int = groupByStatus.get('4').getOrElse(Seq()).length

  private def numberOfServerErrors: Int = groupByStatus.get('5').getOrElse(Seq()).length

  private def numberOfErrorStatus = numberOfClientErrors + numberOfServerErrors

  def serverErrorRate = numberOfServerErrors.toDouble / numberOfErrorStatus.toDouble

  def clientErrorRate = numberOfClientErrors.toDouble / numberOfErrorStatus.toDouble

  def numberOfRequests = requests.length

  def sumSizeOfResponses = requests.map(_.resultSize.get.toInt).filter(_ != None).sum

  def errorRate = numberOfErrorStatus.toDouble / numberOfRequests.toDouble

}
