package com.example.demo.sevices;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final CacheManager cacheManager;


    @Override
    public AuthUser login(String username, String password, HttpServletResponse response) {
        String token = jwtUtils.generateToken(username, password);
        response.setHeader("Authorization",token);
        return authUserRepository.findByUsernameAndActiveTrue(username)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        String username = JwtUtils.getUsername(token);
        authUserRepository.updateOnline(username,false);
        response.setStatus(204);
    }

    @Override
    public AuthUser update(AuthUser user) {
        if (!authUserRepository.existsByUsername(user.getUsername())) {
            throw new NotFoundException();
        }
        AuthUser authUser = authUserRepository.findById(user.getId())
                .orElseThrow(NotFoundException::new);
        if (!user.getUsername().equals(authUser.getUsername())) {
            throw new BadRequestException();
        }
        user.setWorkDate(authUser.getWorkDate());
        user.setEndDate(null);
        user.setOnline(true);
        user.setActive(authUser.getActive());
        user.setRole(authUser.getRole());
        return authUserRepository.save(user);
    }

    @Override
    public AuthUser get(Long id) {
        return authUserRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
