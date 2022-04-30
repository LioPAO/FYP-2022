package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class AddressResponseDTO implements Serializable {

    private Long id;
    private String state;
    private String city;
    private String street;
}
