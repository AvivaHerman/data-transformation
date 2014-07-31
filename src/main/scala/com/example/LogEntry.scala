package com.example

/**
 * Created by Aviva_Herman on 7/30/14.
 */
case class LogEntry(entryStr: String) {
  def parse = {
    case class State(result: Seq[String] = Seq(), balancer: Int = 0)

    def isBalanced(balancer: Int): Boolean = balancer == 0

    def generateNewResult(str: String, state: State): Seq[String] = {
      val (currentResult, balancer) = (state.result, state.balancer)
      if (isBalanced(balancer)) currentResult :+ str else currentResult.init :+ s"""${currentResult.last} ${str}"""
    }

    def calculateNewBalance(str: String, state: State): Int = {
      val (currentResult, balancer) = (state.result, state.balancer)
      if (isBalanced(balancer)) balancer + str.filter(_ == '"').length % 2 else balancer - str.filter(_ == '"').length % 2
    }

    entryStr.split(" ").map(_.trim).filter(_ != "").filter(s => s.last != ']').foldLeft(new State) {
      (state: State, str: String) => State(generateNewResult(str, state), calculateNewBalance(str, state))
    }.result.map(_.stripSuffix("\"")).map(_.stripPrefix("\"")).map(_.stripPrefix("["))
  }
}
