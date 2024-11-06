package uniflee.backend.user.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uniflee.backend.Refresh.Service.RefreshService;
import uniflee.backend.Security.JwtProvider;
import uniflee.backend.user.Dto.PrincipalUserDetails;
import uniflee.backend.user.domain.User;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final RefreshService refreshService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String redirectUrl = "http://localhost:8080/login/success/";
        String accessToken = jwtProvider.createAccessToken(user.getUsername());
        String refreshToken = jwtProvider.createRefreshToken(user.getUsername());
        refreshService.addRefreshToken(user.getUsername(), refreshToken);

        // 내부 sendRedirect가 동작하지 않아 자바스크립트 작성
        String htmlResponse = "<html>" +
                "<head><title>Redirecting...</title></head>" +
                "<body>" +
                "<script type='text/javascript'>" +
                "window.location.href = '" + redirectUrl +
                "accesstoken=" + accessToken +
                "refreshtoken=" + refreshToken + "';" +
                "</script>" +
                "</body>" +
                "</html>";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(htmlResponse);
    }
}