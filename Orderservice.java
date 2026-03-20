package com.cafe.service;

import com.cafe.dto.OrderRequest;
import com.cafe.dto.StatusUpdateRequest;
import com.cafe.model.Order;
import com.cafe.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    // ── Create ────────────────────────────────────────────────────────────────

    public Order placeOrder(OrderRequest req) {
        Order order = new Order();
        order.setCustomerName(req.getCustomerName());
        order.setDrinkType(req.getDrinkType());
        order.setTemp(req.getTemp());
        order.setMilk(req.getMilk());
        order.setMilkAerated(req.isMilkAerated());
        order.setFlavour(req.getFlavour());
        order.setSpecialInstructions(req.getSpecialInstructions());
        order.setStatus(Order.OrderStatus.PENDING);
        return repo.save(order);
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public Order getOrderById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order #" + id + " not found"));
    }

    public List<Order> getActiveOrders() {
        return repo.findByStatusNotInOrderByCreatedAtAsc(
                List.of(Order.OrderStatus.SERVED, Order.OrderStatus.CANCELLED)
        );
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return repo.findByStatusOrderByCreatedAtAsc(status);
    }

    // ── Update status ─────────────────────────────────────────────────────────

    public Order updateStatus(Long id, StatusUpdateRequest req) {
        Order order = getOrderById(id);
        order.setStatus(req.getStatus());
        return repo.save(order);
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(Order.OrderStatus.CANCELLED);
        repo.save(order);
    }
}
