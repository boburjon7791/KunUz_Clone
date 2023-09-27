package com.example.demo.sevices;

import com.example.demo.enums.Role;
import com.example.demo.objects.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DirectorService {
    AuthUser save(AuthUser user);
    void delete(Long id);
    void updatePosition(Role role,Long id);
    Page<AuthUser> employees(Pageable pageable);
}
