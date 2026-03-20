package com.cafe.controller;

import com.cafe.dto.OrderRequest;
import com.cafe.dto.StatusUpdateRequest;
import com.cafe.model.Order;
import com.cafe.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API for the Cafe Barista App
 *
 * Endpoints:
 *   POST   /api/orders              → Place a new order
 *   GET    /api/orders              → Admin: all orders
 *   GET    /api/orders/active       → Barista queue (non-served)
 *   GET    /api/orders/{id}         → Single order detail
 *   PATCH  /api/orders/{id}/status  → Update order status
 *   DELETE /api/orders/{id}         → Cancel an order
 *   GET    /api/orders/enums        → All valid enum values for the UI
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")   // allow the frontend to call in dev
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // ── Place order (customer) ────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody OrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.placeOrder(req));
    }

    // ── All orders (admin) ────────────────────────────────────────────────────
    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    // ── Active queue (barista) ────────────────────────────────────────────────
    @GetMapping("/active")
    public List<Order> getActiveOrders() {
        return service.getActiveOrders();
    }

    // ── Single order ──────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    // ── Update status (barista moves order through pipeline) ──────────────────
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest req) {
        return ResponseEntity.ok(service.updateStatus(id, req));
    }

    // ── Cancel order ──────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        service.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ── Enum values for frontend dropdowns ────────────────────────────────────
    @GetMapping("/enums")
    public Map<String, Object> getEnums() {
        return Map.of(
            "drinkTypes",   Order.DrinkType.values(),
            "temps",        Order.DrinkTemp.values(),
            "milkTypes",    Order.MilkType.values(),
            "flavours",     Order.Flavour.values(),
            "statuses",     Order.OrderStatus.values()
        );
    }
}
