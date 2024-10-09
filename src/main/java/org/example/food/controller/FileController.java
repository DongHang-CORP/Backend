package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService imageService;

    @PostMapping("/image/upload")
    public ResponseEntity<Map<String, Object>> imageUpload(@RequestBody byte[] fileBytes) {
        Map<String, Object> responseData = new HashMap<>();

        try {
            String s3Url = imageService.imageUpload(fileBytes);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);
            return ResponseEntity.ok(responseData);

        } catch (IOException e) {
            responseData.put("uploaded", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
}
