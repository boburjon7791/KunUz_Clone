package com.example.demo.repositories;

import com.example.demo.objects.Category;
import com.example.demo.objects.Post;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findAllByCategory(String category, Pageable pageable);
    Page<Post> findAllByOrderByDateDesc(Pageable pageable);

    Boolean existsByCategory(String name);
}