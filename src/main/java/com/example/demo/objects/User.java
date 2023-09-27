package com.example.demo.objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document
public class User {
    @NotNull
    private Long userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String profileImage;
}
