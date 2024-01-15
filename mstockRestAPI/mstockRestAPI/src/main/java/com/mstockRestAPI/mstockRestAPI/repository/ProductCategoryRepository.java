package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByCategoryName(String name);
    boolean existsByCategoryName(String companyName);
    List<ProductCategory> findByIsActive(Byte isActive);
}
