package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Response.WishResponseDTO;
import com.finalyearproject.fyp.model.Wish;

public class WishMapper {

    public static WishResponseDTO wishToWishResponseDTO(Wish wish){
        WishResponseDTO wishResponseDTO = new WishResponseDTO();
        wishResponseDTO.setAddedOn(wish.getAddedOn());
        wishResponseDTO.setId(wish.getId());
        wishResponseDTO.setProductId(wish.getProduct().getId());
        wishResponseDTO.setUserId(wish.getUser().getId());
        return wishResponseDTO;
    }
}
