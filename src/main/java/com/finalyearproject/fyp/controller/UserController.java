package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //USER============================================================================================================
    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/user/firstname/{firstname}")
    public ResponseEntity<List<UserResponseDTO>> getUserByFirstName(@PathVariable("firstname") String firstname) {
        return ResponseEntity.ok(userService.getUserByFirstName(firstname));
    }

    @GetMapping("/user/lastname/{lastname}")
    public ResponseEntity<List<UserResponseDTO>> getUserByLastName(@PathVariable("lastname") String lastname) {
        return ResponseEntity.ok(userService.getUserByLastName(lastname));
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<List<UserResponseDTO>> getUserByUserName(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUserName(username));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@Valid @PathVariable("id") Long id,
                                             @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Long userid) {
        return userService.deleteUser(userid);
    }

    //ADDRESS====================================================================================================================
    @PostMapping("/add/address/{addressId}/to/user/{userId}")
    public ResponseEntity<String> addAddress(@PathVariable("addressId") Long addressId,
                                             @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.addAddress(addressId, userId));
    }

    @PostMapping("/remove/address/{addressId}/from/user/{userId}")
    public ResponseEntity<String> removeAddress(@PathVariable("addressId") Long addressId,
                                                @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.removeAddress(addressId, userId));
    }

    @GetMapping("/user/address/{userId}")
    public ResponseEntity<Set<AddressResponseDTO>> getAddress(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getAddress(userId));
    }

    @PostMapping("/add/addresses/to/user/{userId}")
    public ResponseEntity<String> addAddresses(@RequestBody List<Long> addressIds, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.addAddresses(addressIds,userId));
    }

    @PostMapping("/remove/addresses/from/user/{userId}")
    public ResponseEntity<String> removeAddress(@RequestBody List<Long> addressIds, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.removeAddress(addressIds,userId));
    }
}
