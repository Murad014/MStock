package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBarcodeRepository extends JpaRepository<ProductBarcode, Long> {
    List<ProductBarcode> findByIsActive(Byte isActive);
    boolean existsByBarcode(String barcode);

}
