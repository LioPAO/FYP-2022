package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.WishRequestDTO;
import com.finalyearproject.fyp.dto.Response.WishResponseDTO;
import com.finalyearproject.fyp.model.Wish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishService {
    WishResponseDTO createWish(WishRequestDTO wishRequestDTO);
    Wish getWish(Long wishId);
    WishResponseDTO getWishById(Long wishId);
    List<WishResponseDTO> getAll();
    String remove(Long wishId);
    List<WishResponseDTO> getWishByUser(Long userId);

}
