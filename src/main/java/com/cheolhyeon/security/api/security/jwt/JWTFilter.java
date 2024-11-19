package com.cheolhyeon.security.api.security.jwt;

import com.cheolhyeon.security.api.security.dto.CustomAPIUserDetails;
import com.cheolhyeon.security.api.security.entity.User;
import com.cheolhyeon.security.api.security.type.AuthorityPolicy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = getAuthorizationValue(request);
        if (isValidToken(bearerToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getToken(bearerToken);
        if (jwtUtil.isExpired(token)) {
            handleExpiredToken(request, response);
            return;
        }
        Authentication auth = createAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String token) {
        String username = jwtUtil.getUsername(token);
        AuthorityPolicy role = AuthorityPolicy.getRole(jwtUtil.getRoleAsString(token));

        User user = new User(username, role);
        CustomAPIUserDetails customUser = CustomAPIUserDetails.from(user);

        // 토큰을 가지고 인증이된 Authentication 객체 생성 후 SecurityContextHolder에 담아준다.
        return new UsernamePasswordAuthenticationToken(customUser,
                null,
                customUser.getAuthorities()
        );
    }

    private static void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Location", "/login");  // 리다이렉트 URL 헤더 추가
        response.getWriter().write("Session expired. Please log in.");
    }

    private static String getToken(String bearerToken) {
        return bearerToken.substring(7);
    }

    private static boolean isValidToken(String bearerToken) {
        return !StringUtils.hasText(bearerToken) || !StringUtils.startsWithIgnoreCase(bearerToken, "Bearer");
    }

    private static String getAuthorizationValue(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
