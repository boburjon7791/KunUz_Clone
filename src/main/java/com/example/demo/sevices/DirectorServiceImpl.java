package com.example.demo.sevices;

import com.example.demo.enums.Role;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    @Override
    public AuthUser save(AuthUser user) {
        if (authUserRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("This username already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setWorkDate(LocalDate.now());
        return authUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        authUserRepository.updateEndDateAndDelete(id);
    }

    @Override
    public void updatePosition(Role role, Long id) {
        authUserRepository.updateAuthUserRoleById(role,id);
    }

    @Override
    public Page<AuthUser> employees(Pageable pageable) {
        return authUserRepository.findAll(pageable);
    }
}
