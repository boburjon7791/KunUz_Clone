package com.example.demo.objects;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false,name = "first_name")
    private String firstName;

    @NotBlank
    @Column(nullable = false,name = "last_name")
    private String lastName;

    @Column(name = "profile_image")
    private String profileImage;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotBlank
    @Column(name = "work_date",nullable = false)
    private LocalDate workDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Builder.Default
    @Column(nullable = false)
    private Boolean active=false;

    @NotNull
    @Column(nullable = false)
    private Boolean online;
}
