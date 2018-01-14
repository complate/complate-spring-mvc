package com.github.complate.spring.mvc;

import com.github.complate.api.ComplateScript;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

public final class ResourceComplateScript implements ComplateScript {

    private final Resource resource;

    public ResourceComplateScript(Resource resource) {
        Assert.notNull(resource, "resource must not be null");
        this.resource = resource;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public String getDescription() {
        return resource.getDescription();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
