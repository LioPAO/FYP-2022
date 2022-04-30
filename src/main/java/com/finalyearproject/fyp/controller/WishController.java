package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.WishRequestDTO;
import com.finalyearproject.fyp.dto.Response.WishResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Wish")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/wish")
    public ResponseEntity<WishResponseDTO> createWish(@Valid @RequestBody WishRequestDTO wishRequestDTO){
        return new ResponseEntity<>(wishService.createWish(wishRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishResponseDTO> getWishById(@PathVariable("id") Long wishId){
        return ResponseEntity.ok(wishService.getWishById(wishId));
    }

    @GetMapping("/")
    public ResponseEntity<List<WishResponseDTO>> getAllWish(){
        return ResponseEntity.ok(wishService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<WishResponseDTO>> getWishByUser(@PathVariable("id") Long userId){
        return ResponseEntity.ok(wishService.getWishByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWish(@PathVariable("id") Long wishId){
        return ResponseEntity.ok(wishService.delete(wishId));
    }

}
