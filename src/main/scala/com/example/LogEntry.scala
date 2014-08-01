package com.example

/**
 * Created by Aviva_Herman on 7/30/14.
 */
case class LogEntry(entryStr: String) {

  case class State(result: Seq[String] = Seq(), balancer: Int = 0) {

    def isBalanced(balancer: Int): Boolean = balancer == 0

    def generateNewResult(str: String): Seq[String] =
      if (isBalanced(balancer)) result :+ str else result.init :+ s"""${result.last} ${str}"""

    def calculateNewBalance(str: String): Int =
      if (isBalanced(balancer)) balancer + str.filter(_ == '"').length % 2 else balancer - str.filter(_ == '"').length % 2

  }

  private def stripUnnecessaryChars: (String) => String = _.stripSuffix("\"").stripPrefix("\"").stripPrefix("[")

  private def splitEntry: Array[String] = entryStr.split(" ").filter(_ != "").filter(s => s.last != ']')

  def parse = splitEntry.foldLeft(new State) {
    (state: State, str: String) => State(state.generateNewResult(str), state.calculateNewBalance(str))
  }.result.map(stripUnnecessaryChars)

  def convertToFields(strings: Seq[String]) = for {
    i <- 0 until strings.length
  } yield (new Field).create(strings(i), i + 1)

  def getRequest = Request(convertToFields(this.parse): _*)

}
