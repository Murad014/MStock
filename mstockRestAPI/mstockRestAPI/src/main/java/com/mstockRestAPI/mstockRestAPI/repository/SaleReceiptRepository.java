package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleReceiptRepository extends JpaRepository<SaleReceipt, Long> {
    List<SaleReceipt> findByCustomer_Name(String customerName);
//    List<SaleReceipt> findByPaymentType(String customerName);
    SaleReceipt findByNumber(String receiptNumber);

}
