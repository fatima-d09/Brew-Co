package com.cafe.repository;

import com.cafe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find by status (for barista queue)
    List<Order> findByStatusOrderByCreatedAtAsc(Order.OrderStatus status);

    // Find all for a customer
    List<Order> findByCustomerNameIgnoreCaseOrderByCreatedAtDesc(String customerName);

    // All active orders (not served/cancelled)
    List<Order> findByStatusNotInOrderByCreatedAtAsc(List<Order.OrderStatus> statuses);
}
