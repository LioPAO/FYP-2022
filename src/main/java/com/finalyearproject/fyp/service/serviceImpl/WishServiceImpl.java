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

    @Transactional
    @Override
    @Caching(
            put = {@CachePut(value = "wish", key = "#result.id")},
            evict = {@CacheEvict(value = "allwishes", allEntries = true),
                    @CacheEvict(value ="wishesPerUser", allEntries = true)}
    )
    public WishResponseDTO createWish(WishRequestDTO wishRequestDTO) {
        User user = userService.getUser(wishRequestDTO.getUserId());
        if (!user.getWish().isEmpty()){
            for (Wish wish : user.getWish()) {
                if (wish.getProduct().getId().equals(wishRequestDTO.getProductId())){
                    return WishMapper.wishToWishResponseDTO(wish); //RETURN WISH IF WISH ALREADY EXISTS
                }
            }
        }
        Product product = productService.getProduct(wishRequestDTO.getProductId());
        Wish wish= new Wish(user,product);
        user.getWish().add(wish);
        wishRepository.save(wish);
        return WishMapper.wishToWishResponseDTO(wish);
    }

    @Override
    public Wish getWish(Long wishId) {
        return wishRepository.findById(wishId)
                .orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.WISH,wishId)));
    }

    @Override
    @Cacheable(value = "wish", key = "#wishId ")
    public WishResponseDTO getWishById(Long wishId) {
        return this.wishRepository.findById(wishId)
                .map(WishMapper::wishToWishResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.WISH,wishId)));
    }

    @Override
    @Cacheable(value = "allwishes", unless = "#result.size() > 100")
    public List<WishResponseDTO> getAll() {
       return wishRepository.findAll()
               .stream()
               .map(WishMapper::wishToWishResponseDTO)
               .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "wish", key = "#wishId"),
            @CacheEvict(value = "allwishes", allEntries = true),
            @CacheEvict(value ="wishesPerUser", allEntries = true)})
    public String delete(Long wishId) {
        Wish wish = this.getWish(wishId);
        wishRepository.delete(wish);
        return Message.removed(ResourceType.WISH);
    }

    @Override
    @Cacheable(value = "wishesPerUser", key= "#userId", unless = "#result.size() > 100")
    public Set<WishResponseDTO> getWishByUser(Long userId) {
        User user = userService.getUser(userId);
        return user.getWish()
                .stream()
                .map(WishMapper::wishToWishResponseDTO)
                .collect(Collectors.toSet());
    }
}
