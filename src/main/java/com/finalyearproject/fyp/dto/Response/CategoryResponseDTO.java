package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
