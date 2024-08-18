package com.home.bakery.services.aws;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface AWSService {
    public void uploadFile(MultipartFile multipartFile, Optional<Map<String, String>> optionalMetaData, String fileName);
    public void uploadFiles(List<MultipartFile> multipartFiles, Optional<Map<String, String>> optionalMetaDatas);
    public String getFileUrl(String fileName);
    public void deleteFile(String fileName);
    public Set<String> getFileUrls(Set<String> fileNames);
}
