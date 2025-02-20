package com.vortexBird.moviePlatform.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface AWSS3Service {
    String uploadFile(MultipartFile file);
    List<String> getObjectsFromS3Bucket();
    InputStream downloadFile(String key);
    void deleteFile(String key);
}
