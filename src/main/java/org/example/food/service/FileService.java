package org.example.food.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.food.common.config.NCPStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FileService {

    private final NCPStorageConfig ncpStorageConfig;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${ncp.storage.localLocation}")
    private String localLocation;

    public String imageUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        // 로컬 파일로 저장
        File localFile = new File(localPath);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            System.err.println("Error saving file to local disk: " + e.getMessage());
            throw e;
        }

        // S3에 파일 업로드
        try {
            ncpStorageConfig.objectStorageClient()
                    .putObject(new PutObjectRequest(bucket, uuidFileName, localFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
            throw e;
        }

        // 업로드된 파일의 URL 가져오기
        String s3Url = ncpStorageConfig.objectStorageClient().getUrl(bucket, uuidFileName).toString();

        // 로컬 파일 삭제
        localFile.delete();

        return s3Url;
    }
}