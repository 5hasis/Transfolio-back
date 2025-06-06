package com.example.transfolio.common.filter;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    //private final String secretKey;

    @Value("${jwt.secret}")
    private String secretKey;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 공개 경로는 필터 통과
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = null;

        // STEP 1. 쿠키에서 JWT Token 추출
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // STEP 2. JWT Token이 없으면 필터 체인을 계속 진행
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401 code
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new ErrorObj(ErrorMessage.UNAUTHORIZED_ACCESS))
            );
            return;
        }

        // STEP 3. JWT Token 만료 여부 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            ErrorObj errorObj = new ErrorObj(ErrorMessage.EXPIRED_JWT_TOKEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(errorObj));
            return;
        }

        // STEP. 4 Jwt Token 에서 LoginId 추출
        String loginId = JwtUtil.getLoginId(token, secretKey);

        // STEP. 5 추출한 loginId로 User 찾아오기
        List<UserEntity> byUserId = userRepository.findByUserId(loginId);
        if (byUserId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new ErrorObj(ErrorMessage.REQUIRED_ID_PASSWORD))
            );
            return;
        }

        UserEntity user = byUserId.get(0);

//      STEP. 6 loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user.getUserId(),
                        null,
                        Collections.emptyList() // 권한이 없는 사용자
                );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//      STEP. 7 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//      STEP. 8 필터 계속 진행
        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/user/sign-in")
                || path.startsWith("/user/sign-up")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/docs/")
                || path.startsWith("/v3/api-docs")
                || path.equals("/");
    }
}
