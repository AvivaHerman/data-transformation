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
      logger.numberOfRequests(logger.allRequests) must_== 11
    }

    "Sum size of all responses" in new Context {
      logger.sumSizeOfResponses(logger.allRequests) must_== 249431
    }

    "Calculate error rate for all requests" in new Context { //TODO: what about req with missing status?
      logger.errorRate(logger.allRequests) must_== 2.0 / 11.0
    }

    "Calculate server error rate" in new Context {
      logger.serverErrorRate(logger.allRequests) must_== 0.5
    }

    "Calculate client error rate" in new Context {
      logger.clientErrorRate(logger.allRequests) must_== 0.5
    }

    "find top 10 URLs" in new Context {
      logger.statistics(logger.topTenURLs) must_== s"""/\t8\t230136\t0.25\n/wpad.dat\t2\t0\t0.0\n/_api/dynamicmodel\t1\t19295\t0.0"""
    }

    "most common IP" in new Context {
      logger.statistics(logger.mostCommonIp) must_== s"""93.89.139.5\t2\t0\t0.0"""
    }

    "most erroneous path" in new Context {
      logger.mostErroneousPath must_== s"""/: client errors: 1, server errors: 1"""
    }

    "summary by hour" in new Context {
      logger.statistics(logger.groupByHour) must_== s"""18/Jul/2014:06:08\t11\t249431\t${2.0/11.0}"""
    }

  }


}
