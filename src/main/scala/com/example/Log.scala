package com.example

/**
 * Created by Aviva_Herman on 8/1/14.
 */
case class Log(path: String) {
  val requests = scala.io.Source.fromFile(path).getLines map (LogEntry(_).getRequest)


}