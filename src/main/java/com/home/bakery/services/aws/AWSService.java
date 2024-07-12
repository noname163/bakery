package com.home.bakery.services.aws;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface AWSService {
    public void uploadFile(MultipartFile multipartFile, Optional<Map<String, String>> optionalMetaData);
}
