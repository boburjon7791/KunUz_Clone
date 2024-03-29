package com.example.demo.sevices;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CustomUserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        try {
            AuthUser authUser = authUserRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(NotFoundException::new);
            return new CustomUserDetails(authUser);
        }catch (NotFoundException e){
            throw new UnauthorizedException();
        }
    }
}
