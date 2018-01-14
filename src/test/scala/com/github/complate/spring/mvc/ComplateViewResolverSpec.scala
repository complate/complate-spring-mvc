package com.github.complate.spring.mvc

import java.util.Locale

import org.scalatest.{MustMatchers, WordSpec}

class ComplateViewResolverSpec extends WordSpec with MustMatchers {

  "The ComplateViewResolver" must {

    "return a view whose content type is HTML and encoding is UTF-8" in {

      val resolver = new ComplateViewResolver()
      val view = resolver.resolveViewName("my-site-index", Locale.ENGLISH)
      view.getContentType mustEqual "text/html; charset=UTF-8"

    }
  }

}
