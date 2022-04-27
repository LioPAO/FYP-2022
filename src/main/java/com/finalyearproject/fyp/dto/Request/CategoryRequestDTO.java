package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Getter
@Setter
public class CategoryRequestDTO {


    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;
    private String description;
    private String imageUrl;
}
