package com.example.demo.sevices;

import com.example.demo.objects.Category;
import com.example.demo.objects.Post;
import org.springframework.stereotype.Service;

@Service
public interface EditorService {
    Post update(Post post);
    void delete(String id);

    String add(String category);
    void deleteCategory(String name);
}
