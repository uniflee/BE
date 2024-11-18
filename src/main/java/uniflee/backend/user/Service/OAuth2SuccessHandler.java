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

        String htmlResponse = "<html>" +
                "<head>" +
                "<title>Redirecting...</title>" +
                "<style>" +
                "body {" +
                "  margin: 0;" +
                "  display: flex;" +
                "  align-items: center;" +
                "  justify-content: center;" +
                "  height: 100vh;" + // Viewport Height: 화면 높이의 100%
                "  background-color: #f4f4f4;" + // 배경색은 선택
                "}" +
                "img {" +
                "  max-width: 100%;" + // 이미지를 가로로 꽉 채움
                "  max-height: 100%;" + // 이미지를 세로로 꽉 채움
                "  object-fit: contain;" + // 비율을 유지하면서 화면에 맞춤
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<img src='/images/complete.png' alt='완료 이미지'>" +
                "<script type='text/javascript'>" +
                "window.location.href = '" + redirectUrl +
                "?t=" + accessToken +
                "&r=" + refreshToken + "';" +
                "window.close();" +
                "</script>" +
                "</body>" +
                "</html>";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(htmlResponse);
    }
}