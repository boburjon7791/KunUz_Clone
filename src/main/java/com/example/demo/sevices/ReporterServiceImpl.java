package com.example.demo.sevices;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.objects.Post;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final MultimediaService multimediaService;

    @Override
    public Post save(Post post) {
        checkImages(post,categoryRepository,multimediaService);
        post.setDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    private static void checkImages(Post post,
                                    CategoryRepository categoryRepository,
                                    MultimediaService multimediaService){
        String category = post.getCategory();
        if (!categoryRepository.existsByName(category)) {
            throw new BadRequestException("Category Not Fount");
        }
        String[] images = post.getImages();
        if (images !=null) {
            List<String> list = Arrays.stream(images)
                    .filter(multimediaService::exist)
                    .toList();
            if (list.size()<images.length) {
                throw new BadRequestException("Some images was not saved");
            }
        }
    }
    @Override
    public Post update(Post post) {
        Post founded = postRepository.findById(post.getId())
                .orElseThrow(NotFoundException::new);
        checkImages(post,categoryRepository,multimediaService);
        post.setTitle(Objects.requireNonNullElse(post.getTitle(),founded.getTitle()));
        post.setBody(Objects.requireNonNullElse(post.getBody(),founded.getBody()));
        post.setImages(Objects.requireNonNullElse(post.getImages(),founded.getImages()));
        post.setVideos(Objects.requireNonNullElse(post.getVideos(),founded.getVideos()));
        return postRepository.save(founded);
    }

    @Override
    public void delete(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        Arrays.stream(post.getImages())
                        .forEach(multimediaService::delete);
        postRepository.delete(post);
    }
}
