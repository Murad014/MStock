package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentCustomerCreditRepository extends JpaRepository<PaymentCustomerCredit, Long> {

}
