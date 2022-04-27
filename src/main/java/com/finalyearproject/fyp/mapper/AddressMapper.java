package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Request.AddressRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.model.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AddressMapper {

    Address addressRequestDTOToAddress(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO addressToAddressResponseDTO(Address address);
}
