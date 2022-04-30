package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductResponseDTO implements Serializable {

    //EXPOSES "WANTED" DATA TO REST API
    private Long id;
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private int cost;
}
