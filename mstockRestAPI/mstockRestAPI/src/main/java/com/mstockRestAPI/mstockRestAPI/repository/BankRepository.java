package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
