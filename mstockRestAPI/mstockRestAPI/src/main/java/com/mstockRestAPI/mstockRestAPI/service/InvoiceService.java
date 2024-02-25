package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceDto add(InvoiceDto invoiceDto);
    InvoiceDto update(Long id, InvoiceDto invoiceDto);
    List<InvoiceDto> fetchAll();
    List<InvoiceDto> fetchByIsActive(Byte isActive);
    InvoiceDto fetchById(Long invoiceId);
    InvoiceDto fetchByInvoiceCode(String invoiceCode);
}
