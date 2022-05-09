package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemRequestDTO {
    @NotNull
    private long userId;
    @NotNull
    private long productId;
    @NotNull
    private int quantity;
}
