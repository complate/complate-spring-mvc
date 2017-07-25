package complate;

import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.core.io.ClassPathResource;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.Optional;

final class NashornScriptingBridge implements ScriptingEngine {

    private final NashornScriptEngine engine = createEngine();

    public void invoke(final String scriptLocation,
                       final String functionName,
                       final Object... args) throws ScriptingException {

        try {
            engine.eval(readScript(new ClassPathResource(scriptLocation)));
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

    private static Reader readScript(final ClassPathResource scriptLocation) {
        try {
            final InputStream is = scriptLocation.getInputStream();
            final Reader isr = new InputStreamReader(is);
            return new BufferedReader(isr);
        } catch (IOException err) {
            throw new ScriptingException(String.format(
                    "failed to read script from classpath, using path '%s'",
                    scriptLocation.getPath()), err);
        }
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