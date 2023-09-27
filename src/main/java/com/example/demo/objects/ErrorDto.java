package com.example.demo.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ErrorDto {
    public int code;
    public String message;
    public String url;
}
