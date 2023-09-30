package com.amand.repository;

import com.amand.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "SELECT o.codeOrder FROM OrderEntity o WHERE o.codeOrder = :codeOrder")
    String findCodeOrderByCodeOrder(@Param("codeOrder") String codeOrder);
}
