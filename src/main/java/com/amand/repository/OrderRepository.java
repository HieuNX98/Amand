package com.amand.repository;

import com.amand.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "SELECT o.codeOrder FROM OrderEntity o WHERE o.codeOrder = :codeOrder")
    String findCodeOrderByCodeOrder(@Param("codeOrder") String codeOrder);

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.status = :status")
    List<OrderEntity> findAllByStatus(@Param("status")Pageable pageable,
                                      Integer status);

    @Query(value = "SELECT count(o) FROM OrderEntity o WHERE o.status = :status")
    int countByStatus(@Param("status") Integer status);

    @Query(value = "UPDATE OrderEntity o SET o.status = 1 WHERE o.id = :id")
    @Modifying
    void updateStatusById(@Param("id") Integer id);
}
