package com.cafe.dto;

import com.cafe.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Drink type is required")
    private Order.DrinkType drinkType;

    @NotNull(message = "Temperature is required")
    private Order.DrinkTemp temp;

    @NotNull(message = "Milk type is required")
    private Order.MilkType milk;

    private boolean milkAerated;

    private Order.Flavour flavour;   // optional

    private String specialInstructions;
}
