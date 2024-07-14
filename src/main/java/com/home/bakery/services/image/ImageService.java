package com.home.bakery.services.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.home.bakery.data.constans.ImageTypes;

public interface ImageService {
    public void saveImages(Object object, ImageTypes imageTypes, List<MultipartFile> multipartFiles);
}
