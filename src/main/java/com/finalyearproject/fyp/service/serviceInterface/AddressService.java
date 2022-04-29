package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.AddressRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.model.Address;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface AddressService {

    @Transactional
    AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO);

    List<AddressResponseDTO> getAllAddress();

    AddressResponseDTO getAddressById(Long id);

    Address getAddress(Long id);

    List<AddressResponseDTO> getAddressByState(String state);

    List<AddressResponseDTO> getAddressByCity(String city);

    @Transactional
    String updateAddress(Long id, AddressRequestDTO Address);

    @Transactional
    String deleteAddressById(Long Id);

    @Transactional
    String deleteAllAddress();
}
