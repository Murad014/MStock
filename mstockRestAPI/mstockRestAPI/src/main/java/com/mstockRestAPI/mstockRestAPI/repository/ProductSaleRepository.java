package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductSale;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {
    List<ProductSale> findBySaleStatus(SaleStatus saleStatus);

}
