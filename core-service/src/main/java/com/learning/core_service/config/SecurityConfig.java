package com.learning.core_service.config;

import com.learning.core_service.sercurity.RpcAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final RpcAuthenticationFilter rpcAuthenticationFilter;

    private static final String[] unauthenticatedRequest = new String[]{
            "/swagger/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/prometheus/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(customizer -> customizer.disable())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(unauthenticatedRequest).permitAll()
                                    .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 401
                        .accessDeniedHandler(new CustomAccessDeniedHandler())         // 403
                );
        http.addFilterBefore(rpcAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
