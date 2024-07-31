package com.home.bakery.services.aws.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.home.bakery.config.AWSConfig;
import com.home.bakery.data.constans.Commons;
import com.home.bakery.services.aws.AWSService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AWSServiceImpl implements AWSService {

    @Value("${s3.bucket.name}")
    private String bucketName;
    private AWSConfig awsConfig;

    public AWSServiceImpl(AWSConfig awsConfig) {
        this.awsConfig = awsConfig;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, Optional<Map<String, String>> optionalMetaData,
            String fileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(metaData -> metaData.forEach(objectMetadata::addUserMetadata));
        objectMetadata.setContentLength(multipartFile.getSize());
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    multipartFile.getInputStream(), objectMetadata);
            AmazonS3 amazonS3 = awsConfig.setUpClient();
            amazonS3.putObject(putObjectRequest);
            log.info("Successfully uploaded file: " + fileName);
        } catch (IOException e) {
            log.error("Error occurred when uploading file: " + fileName, e);
        }
    }

    @Override
    public void uploadFiles(List<MultipartFile> multipartFiles, Optional<Map<String, String>> optionalMetaData) {
        for (int i = 0; i < multipartFiles.size(); ++i) {
            MultipartFile multipartFile = multipartFiles.get(i);
            String baseFileName = optionalMetaData
                    .map(metaData -> metaData.getOrDefault(Commons.IMAGE_NAME, multipartFile.getOriginalFilename()))
                    .orElse(multipartFile.getOriginalFilename());
            String uniqueFileName = baseFileName + "_" + i;
            uploadFile(multipartFile, optionalMetaData, uniqueFileName);
        }
    }
}
