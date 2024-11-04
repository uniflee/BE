package uniflee.backend.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        try {
            if (jwtProvider.validateToken(token)) {
                token = jwtProvider.disassembleToken(token);
                if (!jwtProvider.isAccessToken(token)) {
                    throw new CustomException(ErrorCode.TOKEN_CATEGORY_NOT_MATCHED_ERROR);
                }
                String username = jwtProvider.getUser(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
            filterChain.doFilter(request, response);
    }
}
