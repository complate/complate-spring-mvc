package complate

import java.util.Collections

import org.scalatest.{MustMatchers, WordSpec}
import org.springframework.mock.web.{MockHttpServletRequest, MockHttpServletResponse}

class ComplateViewSpec extends WordSpec with MustMatchers {

  "ComplateView" must {

    "invoke the render function" in {

      val engine = new NashornScriptingBridge
      val scriptLocation = "/views/complate/bundle.js"
      val tag = "my-site-index"
      val model = Collections.singletonMap("title", "ვეპხის ტყაოსანი შოთა რუსთაველი")

      val view = new ComplateView(engine, scriptLocation, tag)

      val request = new MockHttpServletRequest
      val response = new MockHttpServletResponse

      view.render(model, request, response)

      val responseBodyLines = response.getContentAsString.split("\n")

      responseBodyLines.length mustEqual 2
      responseBodyLines(0) mustEqual tag
      responseBodyLines(1) mustEqual model.get("title")

    }
  }

}
