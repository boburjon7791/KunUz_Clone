package com.example.demo.objects;


import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Category {
    @Id
    private String id;
    @NotBlank
    private String name;
}
