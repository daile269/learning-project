package com.learning.core_service.sercurity;

import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RpcAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String role = request.getHeader("role");
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            var auth = new UsernamePasswordAuthenticationToken(
                    username,null, Collections.singleton(new SimpleGrantedAuthority(role))
            );

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Role auth:"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        }

        filterChain.doFilter(request,response);
    }
}
