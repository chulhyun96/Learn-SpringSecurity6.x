package com.cheolhyeon.security.api.security.jwt;

import com.cheolhyeon.security.api.security.dto.CustomAPIUserDetails;
import com.cheolhyeon.security.api.security.dto.LoginForm;
import com.cheolhyeon.security.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    // 요청으로 들어온 아이디와 비밀번호를 가로채서 검증
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트로 부터 들어온 요청(아이디와, 패스워드) -> obtain.. 함수 진행 후 AuthenticationManager 한테 데이터를 전달
        // 데이터를 전달하기전 UsernameAuthenticationToken에 담아서 AuthenticationManager한테 전달

        LoginForm loginForm = getLoginForm(request, objectMapper);

        // username과 password 추출
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        log.info("Attempting Login = {}, {}", username, password);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    // 로그인 성공 시 실행하는 메서드 (successHandler) -> 로그인 성공시 JWT 토큰 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult
    ) throws IOException {
        CustomAPIUserDetails userDetails = (CustomAPIUserDetails) authResult.getPrincipal();
        String username = userDetails.getUsername();

        String role = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // 각 GrantedAuthority에서 권한 문자열을 추출
                .findFirst()  // 첫 번째 권한을 가져옴 (첫 번째가 필요한 경우)
                .orElse(null);  // 권한이 없으면 null 반환

        String token = jwtUtil.generateToken(username, role);
        // 생성된 token을 header에 담아준다. Authorization은 키값이며 value로는 Bearer + 공백 + 생성된 token이 Header로 들어간다
        // 해당 방식은 HTTP 인증 방식 RFC7235의 정의에 따라 아래 인증 형식을 갖추어야 한다.
        response.addHeader("Authorization", "Bearer " + token);
        System.out.println("token = " + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                new ErrorResponse(
                        "Success Login",
                        HttpStatus.OK
                )
        ));

    }
    // 로그인 실패 시 실행하는 메서드 (failureHandler)
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                new ErrorResponse(
                        "로그인 실패!!",
                        HttpStatus.BAD_REQUEST
                )));
    }

    private static LoginForm getLoginForm(HttpServletRequest request, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(request.getInputStream(), LoginForm.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON 데이터 Parsing중 오류 발생 = " + e.getMessage());
        }
    }
}
