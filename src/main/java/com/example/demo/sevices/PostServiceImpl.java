package com.example.demo.sevices;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.objects.Category;
import com.example.demo.objects.Post;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.PostRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String secretKey = "aaegsrhdhsrgsrhdhdrrhdfhdz";


    @Override
    public Post get(String id,
                    HttpServletRequest request,
                    HttpServletResponse response) {

        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        cookie(request,response,passwordEncoder,post,postRepository);
        return post;
    }
    private static void cookie(HttpServletRequest request,
                               HttpServletResponse response,
                               PasswordEncoder passwordEncoder,
                               Post post,
                               PostRepository postRepository){
        Cookie cookie = new Cookie("_value",
                passwordEncoder.encode(secretKey));
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setMaxAge(60*60*24*31*10);
        Cookie[] cookies = request.getCookies();
        Runnable runnable = () -> {
            post.setViewsCount(post.getViewsCount()+1);
            postRepository.save(post);
            response.addCookie(cookie);
        };
        if (cookies==null) {
            runnable.run();
            try {
                Thread.currentThread().join();
            }catch(InterruptedException ignore){}
            return;
        }
        List<Cookie> cookiesList = new LinkedList<>(Arrays.asList(cookies));
        Optional<Cookie> optionalCookie = cookiesList.stream()
                .filter(c -> c.getName().equals("_value"))
                .filter(c -> passwordEncoder.matches(c.getValue(),secretKey))
                .findFirst();
        Consumer<Object> action = o -> {};
        optionalCookie.ifPresentOrElse(action,runnable);
    }

    @Override
    public Page<Post> posts(Pageable pageable, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(NotFoundException::new);
        return postRepository.findAllByCategory(category.getName(),pageable);
    }

    @Override
    public Page<Post> posts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public List<String> categories() {
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    public Page<Post> findBySearching(String search, Pageable pageable) {
        System.out.println(Arrays.toString(search.split("[+]")));
        return postRepository.findAllByInputtedTexts
                (search.split("[+]"),pageable);
    }
}
