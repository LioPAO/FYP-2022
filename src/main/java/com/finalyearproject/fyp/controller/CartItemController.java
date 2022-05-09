package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.CartItemRequestDTO;
import com.finalyearproject.fyp.dto.Response.CartItemResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cartItem")
    public ResponseEntity<CartItemResponseDTO> createCartItem(@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO){
        return new ResponseEntity<>(cartItemService.createCartItem(cartItemRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItemById(@PathVariable("id") Long cartItemId){
        return ResponseEntity.ok(cartItemService.getCartItemById(cartItemId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CartItemResponseDTO>> getAllCartItem(){
        return ResponseEntity.ok(cartItemService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Set<CartItemResponseDTO>> getCartItemByUser(@PathVariable("id") Long userId){
        return ResponseEntity.ok(cartItemService.getCartItemByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("id") Long cartItemId){
        return ResponseEntity.ok(cartItemService.delete(cartItemId));
    }

}
