package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final Converter converter;

    @Autowired
    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            Converter converter
    ){
        this.invoiceRepository = invoiceRepository;
        this.converter = converter;
    }



    @Override
    public InvoiceDto add(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.save(
                converter.mapToEntity(invoiceDto, Invoice.class)
        );

        return converter.mapToDto(invoice, InvoiceDto.class);
    }

    @Override
    public InvoiceDto update(Long invoiceId, InvoiceDto invoiceDto) {
        // Find
        Invoice invoiceFromDB = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("Invoice", "id", invoiceId.toString())
        );

        // Set
        invoiceDto.setId(invoiceId);

        // Update and Return
        return converter.mapToDto(
                invoiceRepository.save(
                    converter.mapToEntity(invoiceDto, Invoice.class)
        ), InvoiceDto.class);
    }

    @Override
    public List<InvoiceDto> fetchAll() {
        return invoiceRepository.findAll().stream()
                .map(invoice -> converter.mapToDto(invoice, InvoiceDto.class))
                .toList();
    }



    @Override
    public InvoiceDto fetchById(Long invoiceId) {
        Invoice invoiceFromDB = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("Invoice", "id", invoiceId.toString())
        );
        return converter.mapToDto(invoiceFromDB, InvoiceDto.class);
    }

    @Override
    public InvoiceDto fetchByInvoiceCode(String invoiceCode) {
        Invoice invoiceFromDB = invoiceRepository.findByInvoiceCode(invoiceCode);
        if(invoiceFromDB == null)
            throw new ResourceNotFoundException("Invoice", "invoiceCode", invoiceCode);

        return converter.mapToDto(invoiceFromDB, InvoiceDto.class);
    }

    @Override
    public List<InvoiceDto> fetchByIsActive(Byte isActive) {
        return invoiceRepository.findByIsActive(isActive)
                .stream()
                .map(invoice -> converter.mapToDto(invoice, InvoiceDto.class))
                .toList();
    }
}
