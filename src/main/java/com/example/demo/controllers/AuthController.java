package com.example.demo.controllers;

import com.example.demo.objects.AuthUser;
import com.example.demo.sevices.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.auth")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AuthController {
    private final AuthService authService;
    @GetMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthUser> login(@RequestParam String username,
                                          @RequestParam String password,
                                          HttpServletResponse response){
        AuthUser authUser = authService.login(username, password, response);
        return ResponseEntity.ok(authUser);
    }
    @PutMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response){
        authService.logout(request,response);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update")
    public ResponseEntity<AuthUser> update(@RequestBody AuthUser user){
        AuthUser authUser = authService.update(user);
        return new ResponseEntity<>(authUser, HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<AuthUser> get(@PathVariable Long id){
        return ResponseEntity.ok(authService.get(id));
    }
}
