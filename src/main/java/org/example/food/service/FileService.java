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

        // 원래 파일 이름을 가져옵니다.
        String fileName = file.getOriginalFilename();

        // 확장자를 추출합니다.
        String ext = fileName.substring(fileName.lastIndexOf("."));

        // 고유한 UUID와 확장자를 결합하여 파일 이름을 만듭니다.
        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        // 로컬 파일로 저장합니다.
        File localFile = new File(localPath);
        file.transferTo(localFile);

        // S3에 파일을 업로드합니다.
        ncpStorageConfig.objectStorageClient()
                .putObject(new PutObjectRequest(bucket, uuidFileName, localFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        // 업로드된 파일의 URL을 가져옵니다.
        String s3Url = ncpStorageConfig.objectStorageClient().getUrl(bucket, uuidFileName).toString();

        // 로컬 파일을 삭제합니다.
        localFile.delete();

        return s3Url;
    }
}