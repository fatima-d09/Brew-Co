package com.cafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String customerName;

    // --- Drink base ---
    @NotNull
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;          // LATTE, CAPPUCCINO, FLAT_WHITE, AMERICANO, MACCHIATO

    @NotNull
    @Enumerated(EnumType.STRING)
    private DrinkTemp temp;               // HOT, ICED

    // --- Milk ---
    @NotNull
    @Enumerated(EnumType.STRING)
    private MilkType milk;                // WHOLE, TWO_PERCENT, OAT, ALMOND, COCONUT, SOY

    private boolean milkAerated;         // foam / microfoam for hot drinks

    // --- Flavour syrup ---
    @Enumerated(EnumType.STRING)
    private Flavour flavour;              // nullable = no syrup

    // --- Notes ---
    private String specialInstructions;

    // --- Status ---
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt  = LocalDateTime.now();
        if (status == null) status = OrderStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ─── Enums ────────────────────────────────────────────────────────────────

    public enum DrinkType {
        LATTE, CAPPUCCINO, FLAT_WHITE, AMERICANO, MACCHIATO
    }

    public enum DrinkTemp {
        HOT, ICED
    }

    public enum MilkType {
        WHOLE, TWO_PERCENT, OAT, ALMOND, COCONUT, SOY
    }

    public enum Flavour {
        BROWN_SUGAR,
        VANILLA,
        CARAMEL,
        MOCHA,
        WHITE_MOCHA,
        PEPPERMINT,
        PEPPERMINT_MOCHA,
        PEPPERMINT_WHITE_MOCHA,
        DARK_CARAMEL
    }

    public enum OrderStatus {
        PENDING,        // order received
        IN_PROGRESS,    // barista started
        AERATION,       // milk being steamed/aerated
        READY,          // drink ready for pickup
        SERVED,         // handed to customer
        CANCELLED
    }
}
