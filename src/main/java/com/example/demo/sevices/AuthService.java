package com.example.demo.sevices;

import com.example.demo.objects.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthUser login(String username, String password, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
    AuthUser update(AuthUser user);
    AuthUser get(Long id);
}
