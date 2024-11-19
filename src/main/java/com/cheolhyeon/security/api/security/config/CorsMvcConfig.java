package com.cheolhyeon.security.api.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 백엔드 API 컨트롤러 요청 매핑에 대해서 허용
                .allowedOrigins("http://localhost:3000");
    }
}
