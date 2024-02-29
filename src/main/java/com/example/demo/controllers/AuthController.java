package com.example.demo.controllers;

import com.example.demo.objects.AuthUser;
import com.example.demo.sevices.AuthService;
import com.example.demo.sevices.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<AuthUser> update(@RequestBody @Valid AuthUser user){
        AuthUser authUser = authService.update(user);
        return new ResponseEntity<>(authUser, HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get")
    public ResponseEntity<AuthUser> get(){
        CustomUserDetails customUserDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(authService.get(customUserDetails.authUser().getId()));
    }
}
