package com.amand.repository;

import com.amand.dto.ProductOrderDto;
import com.amand.entity.ProductOrderEntity;
import com.amand.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Integer> {

    @Query(value = "SELECT p FROM ProductOrderEntity p INNER JOIN p.order o WHERE p.order.id = :orderId AND o.status = :status ")
    List<ProductOrderEntity> findAllByOrderId(@Param("orderId") Integer orderId,
                                              @Param("status") Integer status);

    @Query(value = "SELECT new com.amand.dto.ProductOrderDto(p.name, p.salePrice, p.image1, p.oldPrice, po.amount, " +
                                "po.colorName, po.sizeName) " +
                              "FROM ProductEntity p " +
                              "INNER JOIN p.productOrders po " +
                              "WHERE po.order.id = :orderId")
    List<ProductOrderDto> getProductDto(@Param("orderId") Integer orderId);

}
