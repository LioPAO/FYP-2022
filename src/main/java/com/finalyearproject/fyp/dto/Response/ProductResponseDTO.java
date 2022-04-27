package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    //EXPOSES "WANTED" DATA TO REST API
    private Long id;
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private int cost;
}
