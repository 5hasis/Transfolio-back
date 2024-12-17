package com.example.transfolio.common.filter;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final String secretKey;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // STEP 1. authorization Header에 값이 비어있을경우
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // STEP 2. authorization의 값이 Bearer로 시작하지 않을경우
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // STEP. 3 JWT Token 추출
        String token = authorizationHeader.split(" ")[1];

        // STEP. 4 JWT Token 만료 여부
        if(JwtUtil.isExpired(token, secretKey)) {
            ErrorObj errorObj = new ErrorObj(ErrorMessage.EXPIRED_JWT_TOKEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(errorObj));
            return;
        }

        // STEP. 5 Jwt Token 에서 LoginId 추출
        String loginId = JwtUtil.getLoginId(token, secretKey);

        // 추출한 loginId로 User 찾아오기
        List<UserEntity> byUserId = userRepository.findByUserId(loginId);
        UserEntity user = byUserId.get(0);

//      loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user.getUserId(),
                        null,
                        Collections.emptyList() // 권한이 없는 사용자
                );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//      권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
