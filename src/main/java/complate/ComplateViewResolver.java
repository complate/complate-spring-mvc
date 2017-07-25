package complate;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ComplateViewResolver implements ViewResolver {

    private static final String DEFAULT_SCRIPT_LOCATION =
            "/views/complate/bundle.js";

    private final ScriptingEngine scriptingEngine;

    private final String scriptLocation;

    ComplateViewResolver(final ScriptingEngine scriptingEngine, final String scriptLocation) {
        if (scriptingEngine == null) {
            throw new IllegalArgumentException(
                    "scriptingEngine may not be null");
        }
        if (scriptLocation == null) {
            throw new IllegalArgumentException(
                    "scriptLocation may not be null");
        }
        this.scriptingEngine = scriptingEngine;
        this.scriptLocation = scriptLocation;
    }

    ComplateViewResolver() {
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
