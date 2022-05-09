package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.CartItemRequestDTO;
import com.finalyearproject.fyp.dto.Response.CartItemResponseDTO;
import com.finalyearproject.fyp.model.CartItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public interface CartItemService {
    @Transactional
    CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO);

    CartItem getCartItem(Long cartItemId);

    CartItemResponseDTO getCartItemById(Long cartItemId);

    List<CartItemResponseDTO> getAll();

    @Transactional
    String delete(Long cartItemId);

    Set<CartItemResponseDTO> getCartItemByUser(Long userId);
}
