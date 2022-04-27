package com.finalyearproject.fyp.dto.Request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductRequestDTO {

    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;
    @NotNull(message = "PRICE CANNOT BE EMPTY")
    private int price;
    @NotNull
    private int inventoryQuantity;
    private String description;
    @NotEmpty
    private String imageUrl;
    @NotNull
    private int cost;
}
