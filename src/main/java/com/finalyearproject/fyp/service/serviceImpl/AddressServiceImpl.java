package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.MyUtils;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.AddressRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.AddressMapper;
import com.finalyearproject.fyp.model.Address;
import com.finalyearproject.fyp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AddressServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Transactional
    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO) {
        Address address = addressMapper.addressRequestDTOToAddress(addressRequestDTO);
        addressRepository.save(address);
        return addressMapper.addressToAddressResponseDTO(address);
    }

    @Override
    public Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.ADDRESS,id)));
    }

    @Override
    public AddressResponseDTO getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::addressToAddressResponseDTO)
                .orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.ADDRESS,id)));
    }

    @Override
    public List<AddressResponseDTO> getAllAddress(){
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::addressToAddressResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AddressResponseDTO> getAddressByState(String state) {
        // TODO: 4/27/2022 Write Query to search by name. Do same for other entities
        return addressRepository.findAddressByState(state)
                .stream()
                .map(addressMapper::addressToAddressResponseDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<AddressResponseDTO> getAddressByCity(String city) {
        return addressRepository.findAddressByCity(city)
                .stream()
                .map(addressMapper::addressToAddressResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String updateAddress(Long id, AddressRequestDTO addressRequestDTO) {
        Address update = addressRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.ADDRESS,id)));

        if (MyUtils.isNotEmptyAndNotNull(addressRequestDTO.getCity())){update.setCity(addressRequestDTO.getCity());}
        if (MyUtils.isNotEmptyAndNotNull(addressRequestDTO.getState())){update.setState(addressRequestDTO.getState());}
        if (MyUtils.isNotEmptyAndNotNull(addressRequestDTO.getStreet())){update.setStreet(addressRequestDTO.getStreet());}
        addressRepository.save(update);
        return Message.updated(ResourceType.ADDRESS);
    }
    @Transactional
    @Override
    public String deleteAddress(Long id) {
        this.getAddress(id);
        addressRepository.deleteById(id);
        return Message.deleted(ResourceType.ADDRESS);
    }

    @Override
    public String deleteAddress() {
        addressRepository.deleteAll();
        return Message.deleted(ResourceType.ADDRESSES);
    }
}