package org.example.food.infrastructure.storage.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.infrastructure.storage.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/image/upload")
    public ResponseEntity<Map<String, Object>> imageUpload(@RequestBody byte[] fileBytes) {
        Map<String, Object> responseData = new HashMap<>();

        try {
            String s3Url = fileService.imageUpload(fileBytes);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);
            return ResponseEntity.ok(responseData);

        } catch (IOException e) {
            responseData.put("uploaded", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String url) {
        try {
            fileService.deleteFileFromBucket(url);
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("파일 삭제 실패: " + e.getMessage());
        }
    }
}
