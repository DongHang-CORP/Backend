package org.example.food.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final String BUCKET_NAME = "contest23";
    private static final String DIRECTORY_SEPARATOR = "/";

    private final AmazonS3Client objectStorageClient;

    public String putFileToBucket(MultipartFile file, String fileName, ObjectMetadata objectMetadata) {
        try {
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, fileName, file.getInputStream(), objectMetadata);
            objectStorageClient.putObject(request);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return objectStorageClient.getUrl(BUCKET_NAME, fileName).toString();
    }

    public void deleteFileFromBucket(String url, String dirName) {
        final String[] split = url.split("/");
        final String fileName = dirName + DIRECTORY_SEPARATOR + split[split.length - 1];
        DeleteObjectRequest request = new DeleteObjectRequest(BUCKET_NAME, fileName);
        objectStorageClient.deleteObject(request);
    }

}
