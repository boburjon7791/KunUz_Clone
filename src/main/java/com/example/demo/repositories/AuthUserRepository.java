package com.example.demo.repositories;

import com.example.demo.enums.Role;
import com.example.demo.objects.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, JpaSpecificationExecutor<AuthUser> {
    Boolean existsByUsername(String username);

    Optional<AuthUser> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update AuthUser set online=?2 where username=?1")
    void updateOnline(String username,Boolean status);

    @Modifying
    @Transactional
    @Query(value = "update AuthUser set endDate=now(),active=false where id=?1")
    void updateEndDateAndDelete(Long id);

    @Modifying
    @Transactional
    @Query(value = "update AuthUser set role=?1 where id=?2")
    void updateAuthUserRoleById(Role role, Long id);
}