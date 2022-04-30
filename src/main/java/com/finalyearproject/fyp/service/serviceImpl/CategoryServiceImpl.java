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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(
            put = {@CachePut(value = "category", key = "#result.id")},
            evict = {@CacheEvict(value = "allcategory", allEntries = true)}
    )
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.categoryRequestDTOToCategory(categoryRequestDTO);
        return categoryMapper.categoryToCategoryResponseDTO(categoryRepository.save(category));
    }

    @Override
    @Cacheable(value = "allcategory", unless = "#result.size() > 100")
    public List<CategoryResponseDTO> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "category", key = "#id ")
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
    @Caching(
            put = {@CachePut(value = "category", key = "#id")},
            evict = {@CacheEvict(value = "allcategory", allEntries = true)}
    )
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
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
        System.out.println(Message.updated(ResourceType.CATEGORY));
        return categoryMapper.categoryToCategoryResponseDTO(update);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "allcategory", allEntries = true)})
    public String deleteCategory(Long id) {
        this.getCategory(id);
        categoryRepository.deleteById(id);
        return Message.deleted(ResourceType.CATEGORY);
    }

}
