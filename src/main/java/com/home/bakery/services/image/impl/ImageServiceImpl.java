package com.home.bakery.services.image.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.home.bakery.data.constans.Commons;
import com.home.bakery.data.constans.ImageTypes;
import com.home.bakery.data.entities.Images;
import com.home.bakery.data.repositories.ImageRepository;
import com.home.bakery.services.aws.AWSService;
import com.home.bakery.services.image.ImageService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ImageServiceImpl implements ImageService {

    @Autowired
    private AWSService awsService;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveImages(Object object, ImageTypes imageTypes, List<MultipartFile> multipartFiles) {
        try {
            Method objectMethodGetId = object.getClass().getMethod("getId");
            Long objectId = (Long) objectMethodGetId.invoke(object);
            Method objectMethodGetName = object.getClass().getMethod("getName");
            String objectName = (String) objectMethodGetName.invoke(object);

            String baseFileName = objectName + "_" + objectId;
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", "image/jpeg");
            metadata.put("Content-Disposition", "inline");
            metadata.put("x-amz-meta-title", "My File Title " + objectName + " " + imageTypes);
            metadata.put(Commons.IMAGE_NAME, baseFileName);

            List<Images> images = new ArrayList<>();
            List<MultipartFile> imageFiles = new ArrayList<>();

            for (MultipartFile multipartFile : multipartFiles) {
                if (isImageFile(multipartFile)) {
                    imageFiles.add(multipartFile);
                } else {
                    log.warn("File is not an image: " + multipartFile.getOriginalFilename());
                }
            }

            if (!imageFiles.isEmpty()) {
                awsService.uploadFiles(imageFiles, Optional.of(metadata));
                for (int i = 0; i < imageFiles.size(); i++) {
                    Images image = Images.builder().imageTypes(imageTypes).objectId(objectId)
                            .name(baseFileName + "_" + i+imageFiles.get(i).getContentType().replace("image/", ".")).build();
                    images.add(image);
                }
                imageRepository.saveAll(images);
            } else {
                log.warn("No valid image files to upload.");
            }

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            log.error("Error occurred when saving images for object: " + object.getClass().getName(), e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equalsIgnoreCase("image/jpeg") ||
                contentType.equalsIgnoreCase("image/png") ||
                contentType.equalsIgnoreCase("image/gif") ||
                contentType.equalsIgnoreCase("image/bmp") ||
                contentType.equalsIgnoreCase("image/webp"));
    }

    @Override
    public Set<String> getListImageNameByProductId(Long productId) {
        Optional<Set<Images>> imagesOptional = imageRepository.findImageNameByObjectIdAndImageTypes(productId,
                ImageTypes.PRODUCT);
        Set<String> imageNames = new HashSet<>();
        imagesOptional.ifPresent(images -> {
            images.forEach(image -> imageNames.add(image.getName()));
        });
        return imageNames;
    }

    @Override
    public Set<String> getListURLImageByProductId(Long productId) {
        return awsService.getFileUrls(getListImageNameByProductId(productId));
    }
}
