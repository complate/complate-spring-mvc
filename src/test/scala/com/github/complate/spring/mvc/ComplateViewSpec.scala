package com.github.complate.spring.mvc

import java.util.Collections

import com.github.complate.api.NashornComplateBundle
import org.scalatest.{MustMatchers, WordSpec}
import org.springframework.mock.web.{MockHttpServletRequest, MockHttpServletResponse}

import scala.collection.JavaConverters.mapAsJavaMap

class ComplateViewSpec extends WordSpec with MustMatchers {

  "ComplateView" must {

    "invoke the render function" in {

      val script = ResourceComplateScript.fromClasspath("/views/complate/bundle.js")
      val bundle = new NashornComplateBundle(script)
      val tag = "my-site-index"
      val model = Collections.singletonMap("title", "ვეპხის ტყაოსანი შოთა რუსთაველი")

      val view = new ComplateView(bundle, tag)

      val request = new MockHttpServletRequest
      val response = new MockHttpServletResponse

      view.render(model, request, response)

      val responseBodyLines = response.getContentAsString.split("\n")

      responseBodyLines.length mustEqual 2
      responseBodyLines(0) mustEqual tag
      responseBodyLines(1) mustEqual model.get("title")

    }

    "invoke the render function and assert global object is not undefined" in {

      val script = ResourceComplateScript.fromClasspath("/views/complate/bundle-global-obj.js")
      val bundle = new NashornComplateBundle(script)
      val model = Collections.emptyMap[String,String]()

      val view = new ComplateView(bundle, "")

      val request = new MockHttpServletRequest
      val response = new MockHttpServletResponse

      view.render(model, request, response)

      val responseBodyLines = response.getContentAsString.split("\n")

      responseBodyLines.length mustEqual 1
      responseBodyLines(0) mustEqual "[object global]"

    }

    "provide the bindings set up by the user" in {

      case class TestBinding(prefix: String) {
        def run(s:String): String = s"$prefix: $s World!"
      }

      val script = ResourceComplateScript.fromClasspath("/views/complate/bundle-bindings-test.js")
      val bundle = new NashornComplateBundle(script, mapAsJavaMap(Map(
        ("firstBinding", TestBinding("First binding says")),
        ("secondBinding", TestBinding("Second binding says")))))

      val request = new MockHttpServletRequest
      val response = new MockHttpServletResponse

      val view = new ComplateView(bundle, "irrelevant")

      view.render(null, request, response)

      val responseBodyLines = response.getContentAsString.split("\n")

      responseBodyLines.length mustEqual 2
      responseBodyLines(0) mustEqual "First binding says: Hello World!"
      responseBodyLines(1) mustEqual "Second binding says: Bye World!"
    }
  }

}
