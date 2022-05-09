package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.CartItemRequestDTO;
import com.finalyearproject.fyp.dto.Response.CartItemResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.CartItemMapper;
import com.finalyearproject.fyp.model.CartItem;
import com.finalyearproject.fyp.model.Product;
import com.finalyearproject.fyp.model.User;
import com.finalyearproject.fyp.repository.CartItemRepository;
import com.finalyearproject.fyp.service.serviceInterface.CartItemService;
import com.finalyearproject.fyp.service.serviceInterface.ProductService;
import com.finalyearproject.fyp.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    @Override
    @Caching(
            put = {@CachePut(value = "cartItem", key = "#result.id")},
            evict = {@CacheEvict(value = "allcartItems", allEntries = true),
                    @CacheEvict(value ="cartItemsPerUser", allEntries = true)}
    )
    public CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO) {
        User user = userService.getUser(cartItemRequestDTO.getUserId());
        if (!user.getCartItem().isEmpty()){
            for (CartItem cartItem : user.getCartItem()) {
                if (cartItem.getProduct().getId().equals(cartItemRequestDTO.getProductId())){
                    return CartItemMapper.cartItemToCartItemResponseDTO(cartItem);
                }
            }
        }
        Product product = productService.getProduct(cartItemRequestDTO.getProductId());
        CartItem cartItem= new CartItem(user,product,cartItemRequestDTO.getQuantity());
        user.getCartItem().add(cartItem);
        cartItemRepository.save(cartItem);
        return CartItemMapper.cartItemToCartItemResponseDTO(cartItem);
    }

    @Override
    public CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CART_ITEM,cartItemId)));
    }

    @Override
    @Cacheable(value = "cartItem", key = "#cartItemId ")
    public CartItemResponseDTO getCartItemById(Long cartItemId) {
        return this.cartItemRepository.findById(cartItemId)
                .map(CartItemMapper::cartItemToCartItemResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CART_ITEM,cartItemId)));
    }

    @Override
    @Cacheable(value = "allcartItems", unless = "#result.size() > 100")
    public List<CartItemResponseDTO> getAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(CartItemMapper::cartItemToCartItemResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "cartItem", key = "#cartItemId"),
            @CacheEvict(value = "allcartItems", allEntries = true),
            @CacheEvict(value ="cartItemsPerUser", allEntries = true)})
    public String delete(Long cartItemId) {
        CartItem cartItem = this.getCartItem(cartItemId);
        cartItemRepository.delete(cartItem);
        return Message.removed(ResourceType.CART_ITEM);
    }

    @Override
    @Cacheable(value = "cartItemsPerUser", key= "#userId", unless = "#result.size() > 100")
    public Set<CartItemResponseDTO> getCartItemByUser(Long userId) {
        User user = userService.getUser(userId);
        return user.getCartItem()
                .stream()
                .map(CartItemMapper::cartItemToCartItemResponseDTO)
                .collect(Collectors.toSet());
    }
}
