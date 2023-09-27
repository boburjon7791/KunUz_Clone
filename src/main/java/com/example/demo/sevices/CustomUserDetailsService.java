package com.example.demo.sevices;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(NotFoundException::new);
        return User.builder()
                .username(authUser.getUsername())
                .roles(authUser.getRole().name())
                .password(authUser.getPassword())
                .disabled(false)
                .credentialsExpired(false)
                .accountLocked(authUser.getActive())
                .build();
    }
}
