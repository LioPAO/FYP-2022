package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    Category getCategory(Long id);

    CategoryResponseDTO getCategoryById(Long id);

    List<CategoryResponseDTO> getAllCategory();

    List<CategoryResponseDTO> getCategoryByName(String name);

    String updateCategory(Long id, CategoryRequestDTO category);

    String deleteCategory(Long Id);

    String addProducts(Long categoryId, Long productId);

    String removeProduct(Long categoryId, Long productId);

    String addProducts(Long categoryId, List<Long> productIdList);

    String removeProducts(Long categoryId, List<Long> productIdList);
}
