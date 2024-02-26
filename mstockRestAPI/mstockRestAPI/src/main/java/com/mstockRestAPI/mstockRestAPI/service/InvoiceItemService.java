package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {
    List<InvoiceItemDto> addAll(List<InvoiceItemDto> invoiceList);
    InvoiceItemDto add(InvoiceItemDto invoiceItemDto);
    InvoiceItemDto updateById(Long invoiceItemId, InvoiceItemDto invoiceItemDto);
    Boolean makeDeActiveById(Long invoiceItemId);
}
