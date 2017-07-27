package com.github.complate;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ComplateViewResolver implements ViewResolver {

    private static final String DEFAULT_SCRIPT_LOCATION =
            "/views/complate/bundle.js";

    private final ScriptingEngine scriptingEngine;

    private final Resource bundle;

    public ComplateViewResolver(final ScriptingEngine scriptingEngine,
                                final Resource bundle) {
        Assert.notNull(scriptingEngine, "ScriptingEngine must not be null");
        Assert.notNull(bundle,
            "bundle must not be null");
        this.scriptingEngine = scriptingEngine;
        this.bundle = bundle;
    }

    public ComplateViewResolver() {
        this(new NashornScriptingBridge(),
                new ClassPathResource(DEFAULT_SCRIPT_LOCATION));
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new ComplateView(
                this.scriptingEngine,
                this.bundle,
                viewName);
    }

}
