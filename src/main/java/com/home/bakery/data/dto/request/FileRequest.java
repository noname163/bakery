package com.home.bakery.data.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {
    private MultipartFile file;
    private String fileName;
}
