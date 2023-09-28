package com.example.demo.controllers;

import com.example.demo.sevices.MultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@RequestMapping("/api.multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;
    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> save(@RequestParam MultipartFile file){
        return new ResponseEntity<>(multimediaService.save(file), HttpStatus.CREATED);
    }
    @GetMapping(value = "/get/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] get(@PathVariable String filename){
        return multimediaService.get(filename);
    }
}
