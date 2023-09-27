package com.example.demo.objects;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private LocalDateTime date;

    private String[] images;

    private String[] videos;

    @NotNull
    private String category;

    @NotNull
    private User user;
}
