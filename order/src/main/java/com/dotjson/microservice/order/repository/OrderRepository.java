package com.dotjson.microservice.order.repository;

import com.dotjson.microservice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
