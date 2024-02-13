package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;

import java.util.*;

public interface ProductMovementsService {
    List<ProductMovementDto> addAllSalesOrReturnsProducts(ProductMovementListDto salesOrReturns);



}
