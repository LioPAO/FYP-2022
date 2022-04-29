package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.model.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public interface ProductService {

    @Transactional
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    Product getProduct(Long productId);

    ProductResponseDTO getProductById(Long productId);

    Integer getProductInventory(Long productId);

    List<ProductResponseDTO> getAllProduct();

    List<ProductResponseDTO> getProductByName(String productName);

    List<ProductResponseDTO> getProductByPriceRange(int price, int price2);

    @Transactional
    String updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    @Transactional
    String deleteProduct(Long productId);

    //INVENTORY================================================================

    @Transactional
    Integer setInventoryQuantity(Long productId, int quantity);

    @Transactional
    Integer addInventoryQuantity(Long productId, int quantity);

    @Transactional
    Integer reduceInventoryQuantity(Long productId, int quantity);

    //CATEGORY==================================================================================

    Set<CategoryResponseDTO> getCategory(Long productId);

    @Transactional
    String addCategories(Long productId, List <Long> categoryId);

    @Transactional
    String addCategory(Long productId, Long categoryId);

    @Transactional
    String removeCategories(Long productId, List <Long> categoryId);

    @Transactional
    String removeCategory(Long productId, Long categoryId);

}
