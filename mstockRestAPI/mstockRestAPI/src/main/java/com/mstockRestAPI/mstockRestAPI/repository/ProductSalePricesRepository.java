package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSalePricesRepository extends JpaRepository<ProductSalePrice, Long> {
    List<ProductSalePrice> findByIsActive(Byte isActive);

}
