package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductSalePriceDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;

import java.util.List;

public interface ProductSalePriceService {
    List<ProductSalePriceDto> addList(List<ProductSalePriceDto> list);
    ProductSalePrice add(ProductSalePrice productSalePrice);
    ProductSalePrice update(ProductSalePrice productSalePrice);

}
