package com.home.bakery.services.image.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            String fileName = objectName + "_" + objectId;
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", "image/jpeg");
            metadata.put("Content-Disposition", "inline");
            metadata.put("x-amz-meta-title", "My File Title "+ objectName + " " + imageTypes);
            metadata.put(Commons.IMAGE_NAME, fileName);
            List<Images> images = new ArrayList<>();
            awsService.uploadFiles(multipartFiles, Optional.of(metadata));
            for (int i = 0; i < multipartFiles.size(); i++) {
                Images image = Images.builder().imageTypes(imageTypes).objectId(objectId).name(fileName).build();
                images.add(image);
            }
            imageRepository.saveAll(images);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            log.error("Error occurred when save images: " + object.getClass().getName(), e);
        }

    }

}
