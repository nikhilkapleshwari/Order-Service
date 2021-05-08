package io.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.api.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
