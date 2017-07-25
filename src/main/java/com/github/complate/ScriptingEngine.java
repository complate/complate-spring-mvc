package com.github.complate;

public interface ScriptingEngine {
    void invoke(final String scriptLocation,
                final String functionName,
                final Object... args)
            throws ScriptingException;
}
