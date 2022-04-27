package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.MyUtils;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.CategoryMapper;
import com.finalyearproject.fyp.model.Category;
import com.finalyearproject.fyp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.categoryRequestDTOToCategory(categoryRequestDTO);
        return categoryMapper.categoryToCategoryResponseDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CATEGORY, id)));
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CATEGORY, id)));
    }

    @Override
    public List<CategoryResponseDTO> getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .stream()
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category update = this.getCategory(id);
        if (MyUtils.isNotEmptyAndNotNull(categoryRequestDTO.getName())) {
            update.setName(categoryRequestDTO.getName());
        }
        if (MyUtils.isNotEmptyAndNotNull(categoryRequestDTO.getDescription())) {
            update.setDescription(categoryRequestDTO.getDescription());
        }
        if (MyUtils.isNotEmptyAndNotNull(categoryRequestDTO.getImageUrl())) {
            update.setImageUrl(categoryRequestDTO.getImageUrl());
        }
        categoryRepository.save(update);
        return Message.updated(ResourceType.CATEGORY);
    }

    @Transactional
    @Override
    public String deleteCategory(Long id) {
        this.getCategory(id);
        categoryRepository.deleteById(id);
        return Message.deleted(ResourceType.CATEGORY);
    }

}
