package org.example.food.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.food.common.config.NCPStorageConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
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

    public String imageUpload(byte[] fileBytes) throws IOException {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalArgumentException("File data cannot be null or empty");
        }

        // 파일 이름 및 경로 설정
        String fileName = UUID.randomUUID() + ".png"; // 원하는 확장자에 맞게 수정
        String localPath = localLocation + fileName;

        // 로컬 파일로 저장
        try (FileOutputStream fos = new FileOutputStream(new File(localPath))) {
            fos.write(fileBytes);
        } catch (IOException e) {
            System.err.println("Error saving file to local disk: " + e.getMessage());
            throw e;
        }

        // S3에 파일 업로드
        try {
            ncpStorageConfig.objectStorageClient()
                    .putObject(new PutObjectRequest(bucket, fileName, new File(localPath))
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
            throw e;
        }

        // 업로드된 파일의 URL 가져오기
        String s3Url = ncpStorageConfig.objectStorageClient().getUrl(bucket, fileName).toString();

        // 로컬 파일 삭제
        new File(localPath).delete();

        return s3Url;
    }
}
