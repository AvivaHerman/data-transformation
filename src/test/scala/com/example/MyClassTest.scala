package com.example

import org.specs2.mutable.{After, SpecificationWithJUnit, Specification}
import org.specs2.specification.Scope

/**
 * Created by Aviva_Herman on 7/20/14.
 */
class MyClassTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val myClass = new MyClass
  }

  trait ExtenedContext extends Context {
    val goodbyeLength = myClass.goodbye.length
  }

  trait ContextWithCleanup extends Context with After {
    override def after: Any = println("After out test running cleanup code")

  }

  "MyClass" should {
    "say hello" in new ContextWithCleanup {
      myClass.hello must_== "hello world"
    }

    "say goodbye" in new ExtenedContext {
      myClass.goodbye must_== "Goodbye!"
      goodbyeLength must_== 8
    }
  }







}
