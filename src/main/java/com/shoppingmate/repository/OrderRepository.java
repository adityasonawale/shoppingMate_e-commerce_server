package com.shoppingmate.repository;

import com.shoppingmate.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(" select o from Order o " +
            "where o.user.id = :userId " +
            "and (o.orderStatus = 'PLACED' OR o.orderStatus = 'CONFIRMED' or o.orderStatus = 'SHIPPED' or o.orderStatus = 'DELIVERD')")
    public List<Order> getUsersOrders(@Param("userId") Long userId);
}
