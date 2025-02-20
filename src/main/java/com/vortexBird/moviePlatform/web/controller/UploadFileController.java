package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/public/s3")
public class UploadFileController {
    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value="file")MultipartFile file){
        awss3Service.uploadFile(file);
        String response = "El archivo "+file.getOriginalFilename()+" fue cargado con éxito a S3";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<String>> listFiles(){
        return new ResponseEntity<List<String>>(awss3Service.getObjectsFromS3Bucket(), HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("key")String param){
        InputStreamResource resource = new InputStreamResource(awss3Service.downloadFile(param));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename\""+resource.getFilename()+"\"").body(resource);
    }
}
