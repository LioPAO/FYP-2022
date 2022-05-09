package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Response.CartItemResponseDTO;
import com.finalyearproject.fyp.model.CartItem;

public class CartItemMapper {
    public static CartItemResponseDTO cartItemToCartItemResponseDTO(CartItem cartItem){
        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
        cartItemResponseDTO.setAddedOn(cartItem.getAddedOn());
        cartItemResponseDTO.setId(cartItem.getId());
        cartItemResponseDTO.setProductId(cartItem.getProduct().getId());
        cartItemResponseDTO.setUserId(cartItem.getUser().getId());
        cartItemResponseDTO.setQuantity(cartItem.getQuantity());
        return cartItemResponseDTO;
    }
}
