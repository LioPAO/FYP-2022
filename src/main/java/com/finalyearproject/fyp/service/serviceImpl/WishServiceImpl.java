package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.WishRequestDTO;
import com.finalyearproject.fyp.dto.Response.WishResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.WishMapper;
import com.finalyearproject.fyp.model.Product;
import com.finalyearproject.fyp.model.User;
import com.finalyearproject.fyp.model.Wish;
import com.finalyearproject.fyp.repository.WishRepository;
import com.finalyearproject.fyp.service.serviceInterface.ProductService;
import com.finalyearproject.fyp.service.serviceInterface.UserService;
import com.finalyearproject.fyp.service.serviceInterface.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public WishServiceImpl(WishRepository wishRepository, UserService userService, ProductService productService) {
        this.wishRepository = wishRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public WishResponseDTO createWish(WishRequestDTO wishRequestDTO) {
        User user = userService.getUser(wishRequestDTO.getUserId());
        Product product = productService.getProduct(wishRequestDTO.getProductId());
        Wish wish= new Wish(user,product);
        wishRepository.save(wish);
        return WishMapper.wishToWishResponseDTO(wish);
    }

    @Override
    public Wish getWish(Long wishId) {
        return wishRepository.findById(wishId)
                .orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.WISH,wishId)));
    }

    @Override
    public WishResponseDTO getWishById(Long wishId) {
        return this.wishRepository.findById(wishId)
                .map(WishMapper::wishToWishResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.WISH,wishId)));
    }

    @Override
    public List<WishResponseDTO> getAll() {
       return wishRepository.findAll()
               .stream()
               .map(WishMapper::wishToWishResponseDTO)
               .collect(Collectors.toList());
    }

    @Override
    public String remove(Long wishId) {
        Wish wish = this.getWish(wishId);
        wishRepository.delete(wish);
        return Message.removed(ResourceType.WISH);
    }

    @Override
    public List<WishResponseDTO> getWishByUser(Long userId) {
        User user = userService.getUser(userId);
        return wishRepository.findAllByUserId(userId)
                .stream()
                .map(WishMapper::wishToWishResponseDTO)
                .collect(Collectors.toList());
    }
}
