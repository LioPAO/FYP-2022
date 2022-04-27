package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    Product getProduct(Long productId);

    ProductResponseDTO getProductById(Long productId);

    int getProductInventory(Long productId);

    List<ProductResponseDTO> getAllProduct();

    List<ProductResponseDTO> getProductByName(String productName);

    List<ProductResponseDTO> getProductByPriceRange(int price, int price2);

    String updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    String deleteProduct(Long productId);

    String addCategory(Long productId, Long categoryId);

    String removeCategory(Long productId, Long categoryId);

    String setInventoryQuantity(Long productId, int quantity);

    String addCategories(Long productId, List <Long> categoryId);

    String removeCategories(Long productId, List <Long> categoryId);

    String addInventoryQuantity(Long productId, int quantity);

    String reduceInventoryQuantity(Long productId, int quantity);


}
