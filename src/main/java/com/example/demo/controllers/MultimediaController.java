package com.example.demo.controllers;

import com.example.demo.sevices.MultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam MultipartFile file){
        return new ResponseEntity<>(multimediaService.save(file), HttpStatus.CREATED);
    }
    @GetMapping("/get/{filename}")
    public @ResponseBody byte[] get(@PathVariable String filename){
        return multimediaService.get(filename);
    }
}
