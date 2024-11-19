package com.cheolhyeon.security.api.security.config;

import com.cheolhyeon.security.api.security.jwt.JWTFilter;
import com.cheolhyeon.security.api.security.jwt.JWTUtil;
import com.cheolhyeon.security.api.security.jwt.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigAPI {
    private static final String HTTP_LOCALHOST_3000 = "http://localhost:3000";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "customH2Console")
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer h2Console() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 세션을 Stateless 상태로 관리하기 때문에 CSRF에 대한 것을 disable
        http.cors(cors -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cors = new CorsConfiguration();
                        cors.setAllowedOrigins(Collections.singletonList(HTTP_LOCALHOST_3000));
                        cors.setAllowedMethods(Collections.singletonList("*"));
                        cors.setAllowedHeaders(Collections.singletonList("*"));
                        cors.setAllowCredentials(true);
                        cors.setMaxAge(3600L);

                        // JWT 토큰
                        cors.setExposedHeaders(Collections.singletonList(AUTHORIZATION_HEADER));
                        return cors;
                    }
                }));

        http.csrf(AbstractHttpConfigurer::disable);
        // formLogin disable
        http.formLogin(AbstractHttpConfigurer::disable);
        // http basic 인증방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);
        // 세션 설정 STATELESS 설정하기
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 경로별 인가작업
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
        );
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //원래 기존의 UsernamePasswordAuthenticationFilter를 JWT 토큰을 발급하기 위한 CustomAuthenticationFilter 교체
        http.addFilterAt(new LoginFilter(
                authenticationManager(configuration),
                jwtUtil,
                objectMapper),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
