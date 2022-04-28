package com.finalyearproject.fyp.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressRequestDTO {

    @NotEmpty
    private String state;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
}
