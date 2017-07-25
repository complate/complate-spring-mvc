package complate;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ComplateView implements View {

    private static final String DEFAULT_CONTENT_TYPE =
            "text/html; charset=UTF-8";

    private static final String RENDER_FUNCTION_NAME = "render";

    private final ScriptingEngine scriptingEngine;

    private final String scriptLocation;

    private final String tag;

    ComplateView(final ScriptingEngine scriptingEngine,
                 final String scriptLocation,
                 final String tag) {

        if (scriptingEngine == null) {
            throw new IllegalArgumentException(
                    "scriptingEngine may not be null");
        }
        if (scriptLocation == null) {
            throw new IllegalArgumentException(
                    "scriptLocation may not be null");
        }
        if (tag == null) {
            throw new IllegalArgumentException("tag may not be null");
        }
        
        this.scriptingEngine = scriptingEngine;
        this.scriptLocation = scriptLocation;
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

        final ComplateStream stream = new ServletResponseStream(response);

        this.scriptingEngine.invoke(
                this.scriptLocation,
                RENDER_FUNCTION_NAME,
                stream,
                this.tag,
                model);
    }
}
