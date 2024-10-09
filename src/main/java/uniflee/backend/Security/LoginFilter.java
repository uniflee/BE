package uniflee.backend.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uniflee.backend.designer.domain.CustomDesignerDetails;
import uniflee.backend.designer.domain.Designer;
import uniflee.backend.designer.dto.DesignerInfoResponse;
import uniflee.backend.global.exception.ErrorCode;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomDesignerDetails designerDetails = (CustomDesignerDetails) authResult.getPrincipal();
        Designer designer = designerDetails.getDesigner();
        String accessToken = jwtProvider.createAccessToken(designerDetails.getUsername());

        DesignerInfoResponse designerInfoResponse = DesignerInfoResponse.builder()
                .name(designer.getName())
                .profileImageUrl(designer.getProfileImageUrl())
                .backgroundImageUrl(designer.getBackgroundImageUrl())
                .build();

        response.addHeader("Authorization", accessToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(designerInfoResponse));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(403, ErrorCode.CREDENTIALS_NOT_MATCHED_ERROR.getMessage());
    }
}
