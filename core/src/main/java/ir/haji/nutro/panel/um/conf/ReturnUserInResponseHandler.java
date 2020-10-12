package ir.haji.nutro.panel.um.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Saeed
 */
@Component
public class ReturnUserInResponseHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User currentUser = userService.getCurrentUser();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new String(new ObjectMapper().writeValueAsBytes(currentUser), Charset.forName("utf-8")));
        response.setStatus(200);
    }
}
