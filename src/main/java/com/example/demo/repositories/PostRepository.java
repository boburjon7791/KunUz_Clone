package com.example.demo.repositories;

import com.example.demo.objects.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findAllByCategory(String category, Pageable pageable);

    Boolean existsByCategory(String name);
    @Query("{'$or': [{'title': {'$regex': ?0, '$options': 'i'}}, {'body': {'$regex': ?0, '$options': 'i'}}]}")
    Page<Post> findAllByInputtedTexts(String[] texts, Pageable pageable);
}