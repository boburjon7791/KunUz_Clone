package com.example.demo.sevices;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MultimediaService {
    String save(MultipartFile file);
    boolean exist(String filename);
    void delete(String filename);
    @ResponseBody byte[] get(String filename);
}
