package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceItemRepository;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.repository.ProductRepository;
import com.mstockRestAPI.mstockRestAPI.service.InvoiceItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final Converter converter;

    @Autowired
    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository,
                                  InvoiceRepository invoiceRepository,
                                  ProductRepository productRepository,
                                  Converter converter){
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.converter = converter;
    }


    @Override
    public List<InvoiceItemDto> addAll(List<InvoiceItemDto> invoiceList) {
        List<InvoiceItem> toSaveList = new ArrayList<>();

        for(InvoiceItemDto invoiceItemDto: invoiceList){
            // Find, Set And Save Process
            InvoiceItem invoiceToSave = converter.mapToEntity(invoiceItemDto, InvoiceItem.class);
            findAllAndSet(invoiceItemDto, invoiceToSave);

            // Add
            toSaveList.add(invoiceToSave);
        }

        return invoiceItemRepository.saveAll(toSaveList).stream()
                .map(invoiceItem -> converter.mapToDto(invoiceItem, InvoiceItemDto.class))
                .toList();
    }

    @Override
    public InvoiceItemDto add(InvoiceItemDto invoiceItemDto) {
        // Find And SetProcess
        InvoiceItem invoiceToSave = converter.mapToEntity(invoiceItemDto, InvoiceItem.class);
        findAllAndSet(invoiceItemDto, invoiceToSave);

        // Save
        return converter.mapToDto(
                invoiceItemRepository.save(invoiceToSave),
                InvoiceItemDto.class);
    }

    @Override
    public InvoiceItemDto updateById(Long invoiceItemId, InvoiceItemDto invoiceItemDto) {
        // Find and Set process
        InvoiceItem invoiceItemFromDB = findInvoiceItemById(invoiceItemId);

        invoiceItemFromDB.setQuantity(invoiceItemDto.getQuantity());
        invoiceItemFromDB.setPrice(invoiceItemDto.getPrice());
        invoiceItemFromDB.setIsActive(invoiceItemDto.getIsActive());

        findAllAndSet(invoiceItemDto, invoiceItemFromDB);

        // Save
        return converter.mapToDto(
                invoiceItemRepository.save(invoiceItemFromDB),
                InvoiceItemDto.class
        );
    }

    @Override
    public Boolean makeDeActiveById(Long invoiceItemId) {
        // Find
        InvoiceItem invoiceItemFromDB = findInvoiceItemById(invoiceItemId);

        // Set
        invoiceItemFromDB.setIsActive((byte)0);

        // Save
        invoiceItemRepository.save(invoiceItemFromDB);

        return true;
    }



/* ------------------------------------------------- PRIVATE METHODS ----------------------------------------- */
    private void findAllAndSet(InvoiceItemDto invoiceItemDto, InvoiceItem save) {
        Invoice findInvoice = findInvoiceById(invoiceItemDto.getInvoiceId());
        Product findProduct = findProductById(invoiceItemDto.getProductId());

        save.setInvoice(findInvoice);
        save.setProduct(findProduct);
    }

    private Invoice findInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice", "id", invoiceId.toString()));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product", "id", productId.toString()));
    }

    private InvoiceItem findInvoiceItemById(Long invoiceItemId){
        return invoiceItemRepository.findById(invoiceItemId).orElseThrow(
                () -> new ResourceNotFoundException("InvoiceItem", "id", invoiceItemId.toString())
        );
    }


}
