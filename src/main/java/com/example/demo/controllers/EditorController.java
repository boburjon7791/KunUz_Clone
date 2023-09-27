package com.example.demo.controllers;

import com.example.demo.objects.Post;
import com.example.demo.sevices.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.editor")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EDITOR')")
public class EditorController {
    private final EditorService editorService;
    @PutMapping("/update/post")
    public ResponseEntity<Post> update(@RequestBody Post post){
        Post updated = editorService.update(post);
        return new ResponseEntity<>(updated, HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete/post")
    public ResponseEntity<Void> delete(@RequestParam String id){
        editorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/add/category/{category}")
    public ResponseEntity<String> save(@PathVariable String category){
        return new ResponseEntity<>(editorService.add(category),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/category/{category}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String category){
        editorService.deleteCategory(category);
        return ResponseEntity.noContent().build();
    }
}
