package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;

import java.util.List;
/* We do not need the create DTO for Product Barcode because
it is not available in controller.
Just depends to product. */

public interface ProductBarcodeService {
    ProductBarcodeDto add(ProductBarcodeDto productBarcode);
    ProductBarcodeDto update(ProductBarcodeDto productBarcodeDto);
    void makeIsActiveToDeActive(Long id);
    List<ProductBarcodeDto> findAllByIsActive(Byte isActive);
    boolean existsByBarcode(String barcode);


}
