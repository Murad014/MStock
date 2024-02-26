package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.SupplierOfProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import java.util.*;

public interface SupplierOfProductService {

    SupplierOfProductDto add(SupplierOfProductDto supplierOfProductDto);
    SupplierOfProductDto update(Long supplierId, SupplierOfProductDto supplierOfProductDto);
    List<SupplierOfProductDto> fetchAll();
    SupplierOfProductDto fetchById(Long supplierId);


}
