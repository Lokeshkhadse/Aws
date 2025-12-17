package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /* ================= UPLOAD FILE ================= */
    public String uploadFile(MultipartFile file) throws IOException {

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.getBytes())
        );

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    /* ================= READ FILE ================= */
    public byte[] readFile(String fileName) {

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();

        ResponseInputStream<GetObjectResponse> response =
                s3Client.getObject(getObjectRequest);

        try {
            return response.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from S3", e);
        }
    }

    /* ================= DELETE FILE ================= */
    public String deleteFile(String fileName) {

        DeleteObjectRequest deleteObjectRequest =
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();

        s3Client.deleteObject(deleteObjectRequest);

        return "File deleted: " + fileName;
    }
}
