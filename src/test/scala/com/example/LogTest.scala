package com.example

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by Aviva_Herman on 8/1/14.
 */
class LogTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val logger = Log("/Users/Aviva_Herman/Downloads/request.100th.log")
  }

  "Log" should {

    "get all requests" in new Context {}

  }


}
