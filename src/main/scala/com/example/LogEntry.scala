package com.example

/**
 * Created by Aviva_Herman on 7/30/14.
 */
case class LogEntry(entryStr: String) {

  def getRequest = Request(EntryParser.parse(entryStr): _*)

}

protected[example] case class State(result: Seq[String] = Seq(), balancer: Int = 0) {

  private def isBalanced(balancer: Int): Boolean = balancer == 0

  def generateNewResult(str: String): Seq[String] =
    if (isBalanced(balancer)) result :+ str else result.init :+ s"""${result.last} ${str}"""

  def calculateNewBalance(str: String): Int =
    if (isBalanced(balancer)) balancer + str.filter(_ == '"').length % 2 else balancer - str.filter(_ == '"').length % 2

}

object EntryParser {

  private def stripUnnecessaryChars: (String) => String = _.stripSuffix("\"").stripPrefix("\"").stripPrefix("[")

  private def splitEntry(entryStr: String): Array[String] = entryStr.split(" ").filter(_ != "").filter(s => s.last != ']')

  def parse(entryString: String): Seq[String] = splitEntry(entryString)
    .foldLeft(new State) { (state: State, str: String) =>
      State(state.generateNewResult(str), state.calculateNewBalance(str))
    }
    .result
    .map(stripUnnecessaryChars)

}