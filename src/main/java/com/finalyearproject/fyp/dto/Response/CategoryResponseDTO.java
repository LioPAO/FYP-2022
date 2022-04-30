package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
