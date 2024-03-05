package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    List<ProductPicture> findByIsActive(Byte isActive);

    boolean existsByPictureName(String pictureName);

}
