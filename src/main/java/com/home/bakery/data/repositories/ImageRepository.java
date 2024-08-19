package com.home.bakery.data.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.constans.ImageTypes;
import com.home.bakery.data.entities.Images;

public interface ImageRepository extends JpaRepository<Images, Long> {
    Optional<Set<Images>> findImageNameByObjectIdAndImageTypes(Long objectId, ImageTypes imageTypes);
}
