package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressRequestDTO {

    @NotNull
    private String state;
    @NotNull
    private String city;
    @NotNull
    private String street;
}
