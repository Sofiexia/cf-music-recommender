package com.example.musicrecommend.filter;

import com.example.musicrecommend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//JWT认证过滤器
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    // 白名单路径列表（不需要认证的接口）
    private static final List<String> WHITELIST = List.of(
            "/api/auth/login",
            "/api/auth/register"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        // 如果当前路径在白名单中，则不执行过滤器逻辑
        return WHITELIST.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(header);
        if (authHeader != null && authHeader.startsWith(prefix)) {
            String token = authHeader.substring(prefix.length());
            if (jwtUtil.validateToken(token)) {
                // 将用户ID存入请求属性，供后续使用
                String userId = jwtUtil.getUserIdFromToken(token);
                request.setAttribute("userId", userId);
                Integer userType = jwtUtil.getUserTypeFromToken(token);
                request.setAttribute("userType", userType);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置 401
                response.getWriter().write("Token expired or invalid");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}