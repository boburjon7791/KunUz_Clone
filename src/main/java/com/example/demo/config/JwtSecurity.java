package com.example.demo.config;

import com.example.demo.sevices.CustomUserDetailsService;
import com.example.demo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

import static java.lang.String.format;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtSecurity extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        log.info(format("Remote User %s",request.getRemoteUser()));
        log.info(format("Remote IP address %s",request.getRemoteAddr()));
        log.info(format("Device Details : %s",request.getHeader("User-Agent")));
        if (authorization==null || authorization.isBlank()) {
            filterChain.doFilter(request,response);
            return;
        }
        authorization=authorization.substring(7);
        String username = JwtUtils.getUsername(authorization);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,null,authorities);
        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        log.info(details.toString());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        authenticationToken.setDetails(details);
        filterChain.doFilter(request,response);
    }
}
