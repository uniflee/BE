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

        String redirectUrl = "uniflee:///token";
        String accessToken = jwtProvider.createAccessToken(user.getUsername());
        String refreshToken = jwtProvider.createRefreshToken(user.getUsername());
        refreshService.addRefreshToken(user.getUsername(), refreshToken);

        // 완료 보여주고 싶으면 이거 살리기
        String htmlResponse = "<html>" +
                "<head><title>Redirecting...</title></head>" +
                "<body>" + "완료" +
                "<script type='text/javascript'>" +
                "window.location.href = '" + redirectUrl +
                "?t=" + accessToken +
                "&r=" + refreshToken + "';" + "window.close();" +
                "</script>" +
                "</body>" +
                "</html>";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(htmlResponse);
    }
}