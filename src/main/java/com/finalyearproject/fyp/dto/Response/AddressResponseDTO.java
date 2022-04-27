package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressResponseDTO {

    private Long id;
    @NotNull
    private String state;
    @NotNull
    private String city;
    @NotNull
    private String street;
}
