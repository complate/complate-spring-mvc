package com.github.complate;

import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.core.io.Resource;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.*;
import java.util.Optional;

public final class NashornScriptingBridge implements ScriptingEngine {

    private final NashornScriptEngine engine = createEngine();

    private static final String GLOBAL_OBJ = "var global = this; \n\n";

    public void invoke(final Resource bundle,
                       final String functionName,
                       final Object... args) throws ScriptingException {

        try (Reader reader = readerForScript(bundle)) {
            engine.eval(reader);
        } catch (IOException err) {
            throw new ScriptingException(String.format(
                    "failed to read script from resource '%s'",
                    bundle.getDescription()), err);
        } catch (ScriptException err) {
            throw extractJavaScriptError(err)
                    .map(jsError -> new ScriptingException(
                            "failed to evaluate script",
                            "filepath", functionName, err, jsError))
                    .orElseGet(() -> new ScriptingException(
                            "failed to evaluate script", "filepath",
                            functionName, err));
        }

        try {
            engine.invokeFunction(functionName, args);
        } catch (ScriptException | NoSuchMethodException err) {
            throw extractJavaScriptError(err)
                    .map(jsError -> new ScriptingException(
                            "failed to invoke function",
                            "filepath", functionName, err, jsError))
                    .orElseGet(() -> new ScriptingException(
                            "failed to invoke function", "filepath",
                            functionName, err));
        }
    }

    private static NashornScriptEngine createEngine() {
        final ScriptEngine engine =
                new NashornScriptEngineFactory().getScriptEngine();
        if (engine == null) {
            throw new ScriptingException(
                    "Cannot instantiate Nashorn Script Engine");
        } else {
            return (NashornScriptEngine) engine;
        }
    }

    private static Reader readerForScript(final Resource scriptLocation)
            throws IOException {
        final InputStream is;
        try {
            is = scriptLocation.getInputStream();
        } catch (IOException err) {
            throw new ScriptingException(String.format(
                    "failed to initialize input stream for resource '%s'",
                    scriptLocation.getDescription()), err);
        }
        InputStream stream = prependGlobalObject(is);
        final Reader isr = new InputStreamReader(stream);
        return new BufferedReader(isr);
    }

    private static InputStream prependGlobalObject(InputStream is) {
        InputStream globalObjIS = new ByteArrayInputStream(GLOBAL_OBJ.getBytes());
        return new SequenceInputStream(globalObjIS, is);
    }

    private static Optional<String> extractJavaScriptError(final Exception err) {
        final Throwable cause = err.getCause();
        if (cause instanceof NashornException) {
            return Optional.of(cause.getMessage() + "\n" +
                    NashornException.getScriptStackString(cause));
        } else {
            return Optional.empty();
        }
    }
}
