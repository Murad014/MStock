package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentInvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentInvoice;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.BankCardAccountRepository;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.repository.PaymentInvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.service.PaymentInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInvoiceServiceImpl implements PaymentInvoiceService {

    private final PaymentInvoiceRepository paymentInvoiceRepository;
    private final InvoiceRepository invoiceRepository;
    private final BankCardAccountRepository bankCardAccountRepository;
    private final Converter converter;

    @Autowired
    public PaymentInvoiceServiceImpl(PaymentInvoiceRepository paymentInvoiceRepository,
                                     InvoiceRepository invoiceRepository,
                                     BankCardAccountRepository bankCardAccountRepository,
                                     Converter converter){
        this.paymentInvoiceRepository = paymentInvoiceRepository;
        this.invoiceRepository = invoiceRepository;
        this.bankCardAccountRepository = bankCardAccountRepository;
        this.converter = converter;
    }


    @Override
    public PaymentInvoiceDto add(PaymentInvoiceDto paymentInvoiceDto) {
        // Find All and Set
        PaymentInvoice paymentInvoice = converter.mapToEntity(paymentInvoiceDto, PaymentInvoice.class);
        findAndSetAll(paymentInvoiceDto, paymentInvoice);

        return converter.mapToDto(
                paymentInvoiceRepository.save(paymentInvoice),
                PaymentInvoiceDto.class
        );
    }


/*                              PRIVATE METHODS                         */

    private void findAndSetAll(PaymentInvoiceDto paymentInvoiceDto, PaymentInvoice save){
        save.setInvoice(findInvoiceById(paymentInvoiceDto.getInvoiceId()));
        save.setBankCardAccount(findBankAccountById(paymentInvoiceDto.getBankCardAccountId()));
    }

    private Invoice findInvoiceById(Long invoiceId){
        return invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("Invoice", "id", invoiceId.toString())
        );
    }

    private BankCardAccount findBankAccountById(String bankAccountId){
        return bankCardAccountRepository.findById(bankAccountId).orElseThrow(
                () -> new ResourceNotFoundException("BankAccount", "accountNumber", bankAccountId)
        );

    }


}
