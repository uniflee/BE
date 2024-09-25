package uniflee.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uniflee.backend.user.OAuth2SuccessHandler;
import uniflee.backend.user.Service.OAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // crsf disable
                .csrf().disable()

                //cors disable
                .cors().disable()

                //form login disable
                .formLogin().disable()

                .oauth2Login((auth) -> auth
                        .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))

                // jwt 사용으로 인해 세션 방식을 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().permitAll() // API 개발 후 수정

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)

                // 로그인 필터
                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(
                        new LoginFilter(
                                authenticationManager(authenticationConfiguration), jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
