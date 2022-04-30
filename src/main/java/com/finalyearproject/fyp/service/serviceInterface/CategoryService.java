package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.model.Category;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface CategoryService {

    @Transactional
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    Category getCategory(Long id);

    CategoryResponseDTO getCategoryById(Long id);

    List<CategoryResponseDTO> getAllCategory();

    List<CategoryResponseDTO> getCategoryByName(String name);

    @Transactional
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO category);

    @Transactional
    String deleteCategory(Long Id);
}
