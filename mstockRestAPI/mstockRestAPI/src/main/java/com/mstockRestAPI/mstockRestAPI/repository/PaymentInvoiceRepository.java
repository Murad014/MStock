package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.PaymentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInvoiceRepository extends JpaRepository<PaymentInvoice, Long> {
}
