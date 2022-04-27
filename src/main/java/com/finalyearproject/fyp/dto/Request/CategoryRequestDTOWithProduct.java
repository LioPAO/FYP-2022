package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public class CategoryRequestDTOWithProduct {

    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;
    private String description;
    private String imageUrl;
    @NotNull
    private List<ProductRequestDTO> productRequestDTOList;
}
