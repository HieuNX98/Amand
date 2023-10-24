package com.amand.repository;

import com.amand.dto.PayDto;
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

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.status = :status ORDER BY o.modifiedDate DESC")
    List<OrderEntity> findAllByStatus(@Param("status") Integer status);

    @Query(value = "SELECT count(o) FROM OrderEntity o WHERE o.status = :status")
    int countByStatus(@Param("status") Integer status);

    @Modifying
    @Query(value = "UPDATE OrderEntity o SET o.status = :status WHERE o.id = :id")
    void updateStatusById(@Param("status") Integer status,
                          @Param("id") Integer id);

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.id = :orderId AND o.status = :status")
    OrderEntity findByIdAndStatus(@Param("orderId") Integer orderId,
                                  @Param("status") Integer status);

    @Query(value = "SELECT new com.amand.dto.PayDto(DATE_FORMAT(o.createdDate, '%Y-%m-%d'), SUM(o.totalPrice)) FROM OrderEntity o " +
            "WHERE DATE_FORMAT(o.createdDate, '%Y-%m-%d') = DATE_FORMAT(:createdDate, '%Y-%m-%d') " +
            " GROUP BY DATE_FORMAT(o.createdDate, '%Y-%m-%d')")
    List<PayDto> findAllByDay(@Param("createdDate") String createdDate);

    @Query(value = "SELECT new com.amand.dto.PayDto(DATE_FORMAT(o.createdDate, '%Y-%m-%d'), SUM(o.totalPrice)) FROM OrderEntity o " +
            "WHERE DATE_FORMAT(o.createdDate, '%Y-%m-%d') BETWEEN DATE_FORMAT(:startOfWeek, '%Y-%m-%d') AND DATE_FORMAT(:endOfWeek, '%Y-%m-%d') " +
            " GROUP BY DATE_FORMAT(o.createdDate, '%Y-%m-%d')")
    List<PayDto> findAllByDayToDay(@Param("startOfWeek") String startOfWeek,
                                @Param("endOfWeek") String endOfWeek);

    @Query(value = "SELECT new com.amand.dto.PayDto(DATE_FORMAT(o.createdDate, '%Y-%m'), SUM(o.totalPrice)) FROM OrderEntity o " +
            "WHERE DATE_FORMAT(o.createdDate, '%Y-%m') BETWEEN DATE_FORMAT(:startMonth, '%Y-%m') AND DATE_FORMAT(:endMonth, '%Y-%m') " +
            " GROUP BY DATE_FORMAT(o.createdDate, '%Y-%m')")
    List<PayDto> findAllByMonthToMonth(@Param("startMonth") String startMonth,
                                      @Param("endMonth") String endMonth);

    @Query(value = "SELECT new com.amand.dto.PayDto(DATE_FORMAT(o.createdDate, '%Y'), SUM(o.totalPrice)) FROM OrderEntity o " +
            "WHERE DATE_FORMAT(o.createdDate, '%Y') BETWEEN DATE_FORMAT(:startYear, '%Y') AND DATE_FORMAT(:endYear, '%Y') " +
            " GROUP BY DATE_FORMAT(o.createdDate, '%Y')")
    List<PayDto> findAllByYearToYear(@Param("startYear") String startYear,
                                     @Param("endYear") String endYear);

    @Query(value = "SELECT COUNT(o) FROM OrderEntity o " +
                    "WHERE DATE_FORMAT(o.createdDate, '%Y-%m') = DATE_FORMAT(:createdDate, '%Y-%m') AND o.status = :status")
    int countByCreatedDateAndStatus(@Param("createdDate") String time,
                                    @Param("status") Integer status);

    @Query(value = "SELECT COUNT(o) FROM OrderEntity o " +
                    "WHERE DATE_FORMAT(o.createdDate, '%Y-%m') = DATE_FORMAT(:createdDate, '%Y-%m')")
    int countByCreatedDate(@Param("createdDate") String time);
}
