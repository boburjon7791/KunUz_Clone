package com.example.demo.objects;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Post {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    private Long viewsCount;

    private LocalDateTime date;

    @NotBlank
    private AuthUser authUser;

    private String[] images;

    private String[] videos;
    private String category;
    private User user;
}
