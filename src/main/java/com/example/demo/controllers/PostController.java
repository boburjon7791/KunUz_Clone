package com.example.demo.controllers;

import java.util.List;
import com.example.demo.objects.Post;
import com.example.demo.sevices.PostService;
import com.example.demo.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.post")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PostController {
    private final PostService postService;
    @GetMapping("/get")
    public ResponseEntity<Post> get(@RequestParam String id,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
        Post post = postService.get(id, request, response);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/get-category/{category}")
    public ResponseEntity<Page<Post>> getByCategory(@PathVariable String category,
                                                    @RequestParam(required = false)String page,
                                                    @RequestParam(required = false)String size){
        int[] nums = Utils.number(page, size);
        Page<Post> posts = postService.posts(PageRequest.of(nums[0], nums[1]), category);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/get")
    public ResponseEntity<Page<Post>> get(@RequestParam(required = false)String page,
                                          @RequestParam(required = false)String size){
        int[] nums = Utils.number(page, size);
        Page<Post> posts = postService.posts(PageRequest.of(nums[0], nums[1]));
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<String>> categories(){
        return ResponseEntity.ok(postService.categories());
    }
}
