package com.example.demo.sevices;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class MultimediaServiceImpl implements MultimediaService {
    public static final String root = System.getProperty("user.home")+"/Desktop/files";
    @SneakyThrows
    @Override
    public String save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);
        if (extension==null || !extension.equals("jpg")) {
            throw new BadRequestException("Not Supported file type");
        }
        long megabyte = file.getSize() * 1024 * 1024;
        if (megabyte>1) {
            throw new BadRequestException("Image size must be less than 1 mb");
        }
        InputStream inputStream = file.getInputStream();
        String generatedFilename = UUID.randomUUID() + "." + extension;
        Path path = Path.of(root+"/"+ generatedFilename);
        Files.copy(inputStream, path , StandardCopyOption.REPLACE_EXISTING);
        return generatedFilename;
    }

    @Override
    public boolean exist(String filename) {
        return new File(root + "/" + filename).exists();
    }

    @SneakyThrows
    @Override
    public void delete(String filename) {
        Files.deleteIfExists(Path.of(root + "/" + filename));
    }

    @SneakyThrows
    @Override
    public @ResponseBody  byte[] get(String filename) {
        Path path = Path.of(root + "/" + filename);
        System.out.println(path);
        if (!path.toFile().exists()) {
            throw new NotFoundException();
        }
        return Files.readAllBytes(path);
    }
}
