package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    Product getProduct(Long productId);

    ProductResponseDTO getProductById(Long productId);

    Integer getProductInventory(Long productId);

    List<ProductResponseDTO> getAllProduct();

    List<ProductResponseDTO> getProductByName(String productName);

    List<ProductResponseDTO> getProductByPriceRange(int price, int price2);

    String updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    String deleteProduct(Long productId);

    //INVENTORY================================================================

    Integer setInventoryQuantity(Long productId, int quantity);

    Integer addInventoryQuantity(Long productId, int quantity);

    Integer reduceInventoryQuantity(Long productId, int quantity);

    //CATEGORY==================================================================================

    Set<CategoryResponseDTO> getCategory(Long productId);

    String addCategories(Long productId, List <Long> categoryId);

    String addCategory(Long productId, Long categoryId);

    String removeCategories(Long productId, List <Long> categoryId);

    String removeCategory(Long productId, Long categoryId);

}
