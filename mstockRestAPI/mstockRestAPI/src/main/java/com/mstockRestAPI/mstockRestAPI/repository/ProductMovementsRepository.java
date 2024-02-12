package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Long> {
    List<ProductMovements> findBySaleStatus(SaleStatus saleStatus);

}
