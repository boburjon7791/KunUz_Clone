package com.example.demo.sevices;

import com.example.demo.objects.Post;
import org.springframework.stereotype.Service;

@Service
public interface ReporterService {
    Post save(Post post);
    Post update(Post post);
    void delete(String id);
}
