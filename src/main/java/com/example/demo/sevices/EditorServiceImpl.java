package com.example.demo.sevices;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.objects.Category;
import com.example.demo.objects.Post;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EditorServiceImpl implements EditorService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ReporterService reporterService;

    @Override
    public Post update(Post post) {
        return reporterService.update(post);
    }

    @Override
    public void delete(String id) {
        reporterService.delete(id);
    }

    @Override
    public void deleteCategory(String name) {
        categoryRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
        if (postRepository.existsByCategory(name)) {
            throw new ForbiddenException("""
        You cannot delete this category.
        Because post is exists by this category name
        """);
        }
        categoryRepository.deleteByName(name);
    }

    @Override
    public String add(String category) {
        if (categoryRepository.existsByName(category)) {
            throw new BadRequestException("This category already exist");
        }
        return categoryRepository.save(Category.builder().name(category).build()).getName();
    }
}
