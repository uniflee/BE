package uniflee.backend.user.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uniflee.backend.Security.JwtProvider;
import uniflee.backend.user.Dto.UserInfoResponseDto;
import uniflee.backend.user.Dto.PrincipalUserDetails;
import uniflee.backend.user.domain.User;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.builder()
                .id(user.getId())
                .grade(user.getGrade())
                .name(user.getName())
                .username(user.getUsername())
                .build();

        response.addHeader("accessToken", jwtProvider.createAccessToken(user.getUsername()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userJsonResponse = objectMapper.writeValueAsString(userInfoResponseDto);
        response.getWriter().write(userJsonResponse);
        response.getWriter().flush();
    }
}
