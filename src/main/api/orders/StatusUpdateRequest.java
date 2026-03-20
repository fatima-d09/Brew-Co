package com.cafe.dto;

import com.cafe.model.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private Order.OrderStatus status;
}
