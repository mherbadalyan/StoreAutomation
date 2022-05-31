package com.example.store_automation.repository;

import com.example.store_automation.model.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales,Long> {
}
