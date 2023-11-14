package com.repository;

import com.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user " +
            "LEFT JOIN FETCH o.orderDetail " +
            "WHERE o.id = :id")
    Order findOrderWithUserAndOrderDetail(@Param("id") Long id);

    List<Order> findAllByUser_Id(Long id);
}
