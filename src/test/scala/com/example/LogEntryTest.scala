package com.example

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Aviva_Herman on 7/30/14.
 */
class LogEntryTest extends SpecificationWithJUnit {

  "LogEntry" should {

    "parse to components" in {
      val entry = LogEntry("""93.89.139.5 -  -  [18/Jul/2014:06:08:39 +0000] "GET /wpad.dat HTTP/1.1" 301 0 "-" "Mozilla/5.0 (compatible; IE 11.0; Win32; Trident/7.0)"""")

      entry.parse must_== Seq("93.89.139.5", "-", "-",  "18/Jul/2014:06:08:39", "GET /wpad.dat HTTP/1.1", "301", "0", "-", "Mozilla/5.0 (compatible; IE 11.0; Win32; Trident/7.0)")

    }
  }
}
