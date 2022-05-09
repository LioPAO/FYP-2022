package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.WishRequestDTO;
import com.finalyearproject.fyp.dto.Response.WishResponseDTO;
import com.finalyearproject.fyp.model.Wish;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public interface WishService {

    @Transactional
    WishResponseDTO createWish(WishRequestDTO wishRequestDTO);

    Wish getWish(Long wishId);

    WishResponseDTO getWishById(Long wishId);

    List<WishResponseDTO> getAll();

    @Transactional
    String delete(Long wishId);

    Set<WishResponseDTO> getWishByUser(Long userId);

}
