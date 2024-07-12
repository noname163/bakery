package com.home.bakery.services.aws.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.home.bakery.services.aws.AWSService;

@Service
public class AWSServiceImpl implements AWSService {

    @Value("${aws3.access.key}")
    private String accessKey;
    @Value("${aws3.secret.key}")
    private String secretKey;
    @Value("${aws3.bucket.name}")
    private String bucketName;

    private AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, Optional<Map<String, String>> optionalMetaData) {
        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(Regions.CN_NORTHWEST_1)
                .build();
        File file = new File(accessKey);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            PutObjectRequest putObjectRequest;
            putObjectRequest = new PutObjectRequest(bucketName, accessKey, multipartFile.getInputStream(),objectMetadata);
            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
