package com.github.complate.spring.mvc;

import com.github.complate.api.ComplateBundle;
import com.github.complate.api.ComplateScript;
import com.github.complate.api.NashornComplateBundle;
import com.github.complate.impl.servlet.HttpServletResponseComplateStream;
import com.github.complate.spring.mvc.ComplateView.ComplateStreamFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

public final class ComplateViewResolver implements ViewResolver {

    public static final ComplateScript DEFAULT_SCRIPT =
            new ResourceComplateScript(new ClassPathResource("/templates/complate/bundle.js"));

    private final ComplateBundle bundle;
    private ComplateStreamFactory streamFactory = HttpServletResponseComplateStream::fromResponse;

    public ComplateViewResolver(final Map<String, ?> bindings) {
        this(new NashornComplateBundle(DEFAULT_SCRIPT, bindings));
    }

    public ComplateViewResolver(final ComplateBundle bundle) {
        Assert.notNull(bundle, "bundle must not be null");
        this.bundle = bundle;
    }

    public void setStreamFactory(ComplateStreamFactory streamFactory) {
        Assert.notNull(streamFactory, "streamFactory must not be null");
        this.streamFactory = streamFactory;
    }

    @Override
    public View resolveViewName(final String viewName, final Locale locale) {
        return new ComplateView(bundle, viewName, streamFactory);
    }
}
