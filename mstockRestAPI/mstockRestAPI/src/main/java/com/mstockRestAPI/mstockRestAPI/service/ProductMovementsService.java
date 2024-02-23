package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;

import java.util.*;

public interface ProductMovementsService {
    SaleReceipt addAllSalesOrReturnsProducts(ProductMovementListDto salesOrReturns);



}
