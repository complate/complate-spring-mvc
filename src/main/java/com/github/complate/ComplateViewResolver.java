package com.github.complate;

import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ComplateViewResolver implements ViewResolver {

    private static final String DEFAULT_SCRIPT_LOCATION =
            "/views/complate/bundle.js";

    private final ScriptingEngine scriptingEngine;

    private final String scriptLocation;

    public ComplateViewResolver(final ScriptingEngine scriptingEngine,
                                final String scriptLocation) {
        Assert.notNull(scriptingEngine, "ScriptingEngine must not be null");
        Assert.hasText(scriptLocation,
            "scriptLocation must not be null or empty");
        this.scriptingEngine = scriptingEngine;
        this.scriptLocation = scriptLocation;
    }

    public ComplateViewResolver() {
        this(new NashornScriptingBridge(), DEFAULT_SCRIPT_LOCATION);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new ComplateView(
                this.scriptingEngine,
                this.scriptLocation,
                viewName);
    }

}
