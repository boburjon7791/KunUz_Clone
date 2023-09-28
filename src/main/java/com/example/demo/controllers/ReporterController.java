package com.example.demo.controllers;

import com.example.demo.objects.Post;
import com.example.demo.sevices.ReporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.reporter")
@RequiredArgsConstructor
@PreAuthorize("hasRole('REPORTER')")
public class ReporterController {
    private final ReporterService reporterService;
    @PostMapping("/save/post")
    public ResponseEntity<Post> save(@RequestBody Post post){
        Post saved = reporterService.save(post);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @PutMapping("/update/post")
//    @CachePut(key = "#post.id",value = "posts")
    public ResponseEntity<Post> update(@RequestBody Post post){
        Post updated = reporterService.update(post);
        return new ResponseEntity<>(updated,HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        reporterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
