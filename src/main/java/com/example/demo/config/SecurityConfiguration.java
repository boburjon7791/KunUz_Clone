package com.example.demo.config;

import com.example.demo.objects.ErrorDto;
import com.example.demo.sevices.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
public class SecurityConfiguration {
    public final ObjectMapper objectMapper;
    public final CustomUserDetailsService customUserDetailsService;
    private final JwtSecurity jwtSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry.anyRequest().permitAll())
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler ->
                        handler.accessDeniedHandler((request, response, accessDeniedException) -> {
                            String requestURI = request.getRequestURI();
                            response.setStatus(403);
                            String message = accessDeniedException.getMessage();
                            ErrorDto errorDto = ErrorDto.builder()
                                    .code(403)
                                    .message(message)
                                    .url(requestURI)
                                    .build();
                            ServletOutputStream outputStream = response.getOutputStream();
                            objectMapper.writeValue(outputStream,errorDto);
                            outputStream.close();
                        }))
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint((request, response, accessDeniedException) -> {
                            String requestURI = request.getRequestURI();
                            response.setStatus(401);
                            String message = accessDeniedException.getMessage();
                            ErrorDto errorDto = ErrorDto.builder()
                                    .code(401)
                                    .message(message)
                                    .url(requestURI)
                                    .build();
                            ServletOutputStream outputStream = response.getOutputStream();
                            objectMapper.writeValue(outputStream,errorDto);
                            outputStream.close();
                        }))
                .addFilterBefore(jwtSecurity, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                "/**"
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
