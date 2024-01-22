package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.queries.ProductQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActive(Byte isActive);

    @Query(value = ProductQueries.FIND_BY_BARCODE_AND_IS_ACTIVE)
    List<Product> findByBarcode(@Param("barcode") String barcode,
                                @Param("isActive") byte isActive);

    @Query(value = ProductQueries.FIND_BY_PRODUCT_NAME_WITH_LIKE_AND_IS_ACTIVE)
    List<Product> findByProductName(@Param("productName") String productName,
                                    @Param("isActive") byte isActive);
}
