package com.tkmind.ecommerce.order.repo;

import com.tkmind.ecommerce.order.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
