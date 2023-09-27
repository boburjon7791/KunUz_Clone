package com.example.demo.sevices;

import com.example.demo.objects.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post get(String id, HttpServletRequest request, HttpServletResponse response);
    Page<Post> posts(Pageable pageable, String category);
    Page<Post> posts(Pageable pageable);
    List<String> categories();
    Page<Post> findBySearching(String search, Pageable pageable);
}
