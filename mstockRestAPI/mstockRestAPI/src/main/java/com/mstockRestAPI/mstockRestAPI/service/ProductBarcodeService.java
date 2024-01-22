package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;

import java.util.List;
/* We do not need the create DTO for Product Barcode because
it is not available in controller.
Just depends to product. */

public interface ProductBarcodeService {
    ProductBarcode add(ProductBarcode productBarcode);
    ProductBarcode update(ProductBarcode productBarcode);
    void makeIsActiveToDeActive(Long id);
    List<ProductBarcode> findAllByIsActive(Byte isActive);
    boolean existsByBarcode(String barcode);


}
