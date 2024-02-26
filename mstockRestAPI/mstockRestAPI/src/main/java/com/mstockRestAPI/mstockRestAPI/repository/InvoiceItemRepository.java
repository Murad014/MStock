package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    List<InvoiceItem> findByInvoice_invoiceCode(String invoiceCode);

}
