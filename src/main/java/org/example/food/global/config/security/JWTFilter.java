package org.example.food.global.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.food.user.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTFilter(JWTUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        try {
            // 토큰 만료 여부 검증
            if (jwtUtil.isExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
                response.setContentType("application/json");              // JSON 형식 설정
                response.getOutputStream().print("{\"error\": \"Token expired\"}"); // 본문에 메시지 전송
                return;
            }

            String email = jwtUtil.getEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByEmail(email);
            Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT processing error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getOutputStream().print("{\"error\": \"Invalid token\"}");
        }
    }


}
