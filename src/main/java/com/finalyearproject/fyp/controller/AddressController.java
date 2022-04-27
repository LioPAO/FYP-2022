package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.AddressRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //CREATE NEW ADDRESS
    @PostMapping("/address")
    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO){
        return new ResponseEntity<>(addressService.createAddress(addressRequestDTO), HttpStatus.CREATED);
    }

    //GET REQUESTS
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable("id") Long id ){
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddress(){
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/address/state/{state}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressByState(@PathVariable("state") String stateName ){
        return ResponseEntity.ok(addressService.getAddressByState(stateName));
    }

    @GetMapping("/address/city/{city}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressByCity(@PathVariable("city") String cityName ){
        return ResponseEntity.ok(addressService.getAddressByCity(cityName));
    }

    //UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id,   @RequestBody AddressRequestDTO addressRequestDTO){
        return ResponseEntity.ok(addressService.updateAddress(id,addressRequestDTO));
    }

    //DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        return addressService.deleteAddress(id);
    }


    @DeleteMapping("/")
    public String deleteUser(){
        return addressService.deleteAddress();
    }
}
