package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.AddressRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.model.Address;

import java.util.List;

public interface AddressService {

    AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO);

    List<AddressResponseDTO> getAllAddress();

    AddressResponseDTO getAddressById(Long id);

    Address getAddress(Long id);

    List<AddressResponseDTO> getAddressByState(String state);

    List<AddressResponseDTO> getAddressByCity(String city);

    String updateAddress(Long id, AddressRequestDTO Address);

    String deleteAddress(Long Id);
}
