package com.github.complate.spring.mvc;

import com.github.complate.api.ComplateBundle;
import com.github.complate.api.ComplateStream;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public final class ComplateView implements View {

    private static final String DEFAULT_CONTENT_TYPE = "text/html; charset=UTF-8";

    private final ComplateBundle bundle;
    private final String tag;
    private final ComplateStreamFactory streamFactory;

    ComplateView(final ComplateBundle bundle, final String tag,
            final ComplateStreamFactory streamFactory) {
        Assert.notNull(bundle, "bundle must not be null");
        Assert.notNull(tag, "tag must not be null");
        Assert.notNull(streamFactory, "streamFactory must not be null");
        this.bundle = bundle;
        this.tag = tag;
        this.streamFactory = streamFactory;
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

        final ComplateStream stream = streamFactory.from(response);

        this.bundle.render(stream, tag, model);
    }

    @FunctionalInterface
    public interface ComplateStreamFactory {
        ComplateStream from(HttpServletResponse response) throws IOException;
    }
}
