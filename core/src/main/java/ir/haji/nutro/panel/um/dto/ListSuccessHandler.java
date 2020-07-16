package ir.haji.nutro.panel.um.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    List<SimpleUrlAuthenticationSuccessHandler> handlers;

    public ListSuccessHandler(List<SimpleUrlAuthenticationSuccessHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (SimpleUrlAuthenticationSuccessHandler handler : handlers) {
            handler.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
