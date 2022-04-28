package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressResponseDTO {

    private Long id;
    private String state;
    private String city;
    private String street;
}
