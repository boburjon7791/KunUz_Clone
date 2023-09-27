package com.example.demo.repositories;

import com.example.demo.objects.Category;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Boolean existsByName(String name);

    Optional<Category> findByName(String category);

    @Modifying
    @Transactional
    void deleteByName(String name);
}