package com.amand.repository;

import com.amand.entity.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Integer> {
}
