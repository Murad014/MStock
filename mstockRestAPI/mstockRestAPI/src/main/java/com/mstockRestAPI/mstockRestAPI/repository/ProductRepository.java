package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.queries.ProductQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.mstockRestAPI.mstockRestAPI.queries.ProductQueries.FIND_BY_PRODUCT_NAME_AND_PRODUCT_BARCODE_AND_CATEGORY_AND_IS_ACTIVE;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActive(Byte isActive);

    @Query(value = ProductQueries.FIND_BY_BARCODE_AND_IS_ACTIVE)
    List<Product> findByBarcode(@Param("barcode") String barcode,
                                @Param("isActive") byte isActive);

    @Query(value = ProductQueries.FIND_BY_PRODUCT_NAME_WITH_LIKE_AND_IS_ACTIVE)
    List<Product> findByProductName(@Param("productName") String productName,
                                    @Param("isActive") byte isActive);

    @Query(value = FIND_BY_PRODUCT_NAME_AND_PRODUCT_BARCODE_AND_CATEGORY_AND_IS_ACTIVE)
    Page<Product> findByBarcodeAndOtherFields(@Param("barcode") String barcode,
                                              @Param("productName") String productName,
                                              @Param("category") String category,
                                              @Param("isActive") String isActive,
                                              Pageable pageable
    );

}
