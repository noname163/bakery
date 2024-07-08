package com.home.bakery.services.aws.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.home.bakery.services.aws.AWSService;

@Service
public class AWSServiceImpl implements AWSService {

    @Value("${aws3.access.key}")
    private String accessKey;
    @Value("${aws3.secret.key}")
    private String secretKey;

    private AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Override
    public void uploadFile() {
        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(Regions.CN_NORTHWEST_1)
                .build();
        amazonS3Client.putObject(null);
    }

}
