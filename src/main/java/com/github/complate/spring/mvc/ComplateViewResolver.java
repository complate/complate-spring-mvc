package com.github.complate.spring.mvc;

import com.github.complate.api.ComplateEngine;
import com.github.complate.spring.mvc.ComplateView;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public final class ComplateViewResolver implements ViewResolver {

    public static final Resource DEFAULT_BUNDLE =
            new ClassPathResource("/templates/complate/bundle.js");

    private final ComplateEngine engine;

    private final Resource script;

    public ComplateViewResolver() {
        this(DEFAULT_BUNDLE);
    }

    public ComplateViewResolver(final Resource script) {
        this(ComplateEngine.create(), script);
    }

    public ComplateViewResolver(final ComplateEngine engine, final Resource script) {
        Assert.notNull(engine, "engine must not be null");
        Assert.notNull(script, "script must not be null");
        this.engine = engine;
        this.script = script;
    }

    @Override
    public View resolveViewName(final String viewName, final Locale locale) {
        return new ComplateView(engine, script, viewName);
    }
}
