package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class WishRequestDTO {


    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
}
