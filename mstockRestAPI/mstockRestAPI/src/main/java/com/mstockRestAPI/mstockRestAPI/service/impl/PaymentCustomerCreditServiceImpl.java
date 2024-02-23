package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CreditsOfCustomersRepository;
import com.mstockRestAPI.mstockRestAPI.repository.PaymentCustomerCreditRepository;
import com.mstockRestAPI.mstockRestAPI.service.PaymentCustomerCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCustomerCreditServiceImpl implements PaymentCustomerCreditService {

    private final PaymentCustomerCreditRepository paymentCustomerCreditRepository;
    private final CreditsOfCustomersRepository creditsOfCustomersRepository;
    private final Converter converter;

    @Autowired
    public PaymentCustomerCreditServiceImpl(PaymentCustomerCreditRepository paymentCustomerCreditRepository,
                                            CreditsOfCustomersRepository creditsOfCustomersRepository,
                                            Converter converter){
        this.paymentCustomerCreditRepository = paymentCustomerCreditRepository;
        this.creditsOfCustomersRepository = creditsOfCustomersRepository;
        this.converter = converter;
    }

    @Override
    public PaymentCustomerCreditDto add(Long creditId, PaymentCustomerCreditDto paymentCustomerCredit) {

        CreditOfCustomers credit = creditsOfCustomersRepository.findById(creditId).orElseThrow(
                () -> new ResourceNotFoundException("CreditsOfCustomers", "id", creditId.toString())
        );

        paymentCustomerCredit.setCredit(
                converter.mapToDto(credit, CreditOfCustomersDto.class)
        );
        PaymentCustomerCredit saved = paymentCustomerCreditRepository.save(
                converter.mapToEntity(paymentCustomerCredit, PaymentCustomerCredit.class)
        );

        return converter.mapToDto(saved, PaymentCustomerCreditDto.class);
    }
}
