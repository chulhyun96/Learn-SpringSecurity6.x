package com.cheolhyeon.security.config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth
                // 인덱스 페이지,로그인 페이지,회원가입 페이지,회원가입 절차를 진행할 API 호출의 경로를 넣어줌
                .requestMatchers("/", "/login","/join","/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );
        http.formLogin(auth -> auth.loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll()
        );
        // csrf -> 얘는 Post 요청시 토큰을 보내줘야됨. 현재 개발 환경에서는 토큰을 사용하지 않으니 disable
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
