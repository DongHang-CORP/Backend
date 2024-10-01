package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService imageService;

    @PostMapping("/image/upload")
    public Map<String, Object> imageUpload(@RequestParam("file") MultipartFile request) throws Exception {

        Map<String, Object> responseData = new HashMap<>();

        try {

            String s3Url = imageService.imageUpload(request);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);

            return responseData;

        } catch (IOException e) {

            responseData.put("uploaded", false);

            return responseData;
        }
    }
}