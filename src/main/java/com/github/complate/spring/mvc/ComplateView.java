package com.github.complate.spring.mvc;

import com.github.complate.api.ComplateEngine;
import com.github.complate.api.ComplateScript;
import com.github.complate.api.ComplateStream;
import com.github.complate.impl.servlet.HttpServletResponseComplateStream;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public final class ComplateView implements View {

    private static final String DEFAULT_CONTENT_TYPE = "text/html; charset=UTF-8";

    private final ComplateEngine engine;
    private final ComplateScript script;
    private final String tag;

    ComplateView (final ComplateEngine engine, final Resource script, final String tag) {
        this(engine, new ResourceComplateScript(script), tag);
    }

    private ComplateView(final ComplateEngine engine, final ComplateScript script, final String tag) {
        Assert.notNull(engine, "engine must not be null");
        Assert.notNull(script, "script must not be null");
        Assert.notNull(tag, "tag must not be null");
        this.engine = engine;
        this.script = script;
        this.tag = tag;
    }

    @Override
    public String getContentType() {
        return DEFAULT_CONTENT_TYPE;
    }

    @Override
    public void render(final Map<String, ?> model,
                       final HttpServletRequest request,
                       final HttpServletResponse response) throws Exception {

        response.setContentType(DEFAULT_CONTENT_TYPE);

        final ComplateStream stream = HttpServletResponseComplateStream.fromResponse(response);

        this.engine.invoke(script, stream, tag, model);
    }
}
