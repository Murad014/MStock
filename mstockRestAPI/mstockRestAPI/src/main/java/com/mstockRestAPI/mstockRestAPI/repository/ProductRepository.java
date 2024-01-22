package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActive(Byte isActive);

    @Query("SELECT p FROM Product p JOIN p.productBarcodeList b WHERE b.barcode = :barcode")
    List<Product> findByBarcode(@Param("barcode") String barcode);
}
