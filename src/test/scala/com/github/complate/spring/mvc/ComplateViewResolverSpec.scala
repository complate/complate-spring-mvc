package com.github.complate.spring.mvc

import java.util
import java.util.Locale

import com.github.complate.api.{ComplateBundle, ComplateStream}
import org.scalatest.{MustMatchers, WordSpec}

class ComplateViewResolverSpec extends WordSpec with MustMatchers {

  "The ComplateViewResolver" must {

    "return a view whose content type is HTML and encoding is UTF-8" in {

      val resolver = new ComplateViewResolver(new MockComplateBundle)
      val view = resolver.resolveViewName("my-site-index", Locale.ENGLISH)
      view.getContentType mustEqual "text/html; charset=UTF-8"

    }
  }

  class MockComplateBundle extends ComplateBundle {
    override def render(complateStream: ComplateStream, s: String, map: util.Map[String, _]): Unit = ???
  }
}
