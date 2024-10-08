package com.home.bakery.services.aws.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
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

    @Value("${s3.download.url}")
    private String downloadURL;

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
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    fileName + multipartFile.getContentType().replace("image/", "."),
                    multipartFile.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
            AmazonS3 amazonS3 = awsConfig.setUpClient();
            deleteFile(fileName);
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

    @Override
    public String getFileUrl(String fileName) {
        // Date expiration = new Date();
        // long expTimeMillis = expiration.getTime();
        // expTimeMillis += 1000 * 60 * 60;
        // expiration.setTime(expTimeMillis);

        // GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
        //         .withMethod(com.amazonaws.HttpMethod.GET)
        //         .withExpiration(expiration);
        // AmazonS3 amazonS3Client = awsConfig.setUpClient();
        // URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        // return url.toString();
        return downloadURL+fileName;
    }

    @Override
    public Set<String> getFileUrls(Set<String> fileNames) {
        return fileNames.stream().map(this::getFileUrl).collect(Collectors.toSet());
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            AmazonS3 amazonS3 = awsConfig.setUpClient();
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
            if (amazonS3.doesObjectExist(bucketName, fileName)) {
                amazonS3.deleteObject(deleteObjectRequest);
                log.info("Delete file name " + fileName + " success");
            } else {
                log.warn("Cannot find image with name " + fileName);
            }
        } catch (Exception e) {
            log.error("Error while remove file name " + fileName);
            log.error(e.getMessage());
        }
    }
}
