package com.cheolhyeon.security;

import com.cheolhyeon.security.api.security.config.SecurityConfigAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = "com.cheolhyeon.security.api")
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
        // TODO : 회원가입 로직 작성하면 됨. 16:16 부분부터 보면됨
    }

}
