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
      logger.getNumberOfRequests must_== 10
    }

  }


}
