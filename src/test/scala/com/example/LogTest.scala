package com.example

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by Aviva_Herman on 8/1/14.
 */
class LogTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val logger = Log("/Users/Aviva_Herman/dev/dataTrans/src/test/scala/com/example/logger_sample.txt")
  }

  "Log" should {

    "Count all requests" in new Context {
      logger.numberOfRequests must_== 10
    }

    "Sum size of all responses" in new Context {
      logger.sumSizeOfResponses must_== 249431
    }

    "Calculate error rate for all requests" in new Context { //TODO: what about req with missin status?
      logger.errorRate must_== 0.2
    }

    "Calculate server error rate" in new Context {
      logger.serverErrorRate must_== 0.5
    }

    "Calculate client error rate" in new Context {
      logger.clientErrorRate must_== 0.5
    }

  }


}
