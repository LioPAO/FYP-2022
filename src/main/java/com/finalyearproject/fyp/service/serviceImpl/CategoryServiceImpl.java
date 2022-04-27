package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.CategoryMapper;
import com.finalyearproject.fyp.model.Category;
import com.finalyearproject.fyp.repository.CategoryRepository;
import com.finalyearproject.fyp.service.serviceInterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
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
        Category update = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CATEGORY, id)));

        update.setName(categoryRequestDTO.getName());
        update.setDescription(categoryRequestDTO.getDescription());
        update.setImageUrl(categoryRequestDTO.getImageUrl());
        categoryRepository.save(update);
        return Message.updated(ResourceType.CATEGORY);
    }

    @Transactional
    @Override
    public String deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.CATEGORY, id)));
        categoryRepository.deleteById(id);
        return Message.deleted(ResourceType.CATEGORY);
    }

    @Override
    public String addProducts(Long categoryId, Long productId) {
        getCategory(categoryId).addProduct(productService.getProduct(productId));
        return Message.added(ResourceType.PRODUCT);
    }

    @Override
    public String removeProduct(Long categoryId, Long productId) {
        getCategory(categoryId).removeProduct(productService.getProduct(productId));
        return Message.removed(ResourceType.PRODUCT);
    }

    @Override
    public String addProducts(Long categoryId, List<Long> productIdList) {
        Category category = getCategory(categoryId);
        productIdList.stream().map(productService::getProduct).forEach(category::addProduct);
        return Message.added(ResourceType.PRODUCTS);
    }

    @Override
    public String removeProducts(Long categoryId, List<Long> productIdList) {
        Category category = getCategory(categoryId);
        productIdList.stream().map(productService::getProduct).forEach(category::removeProduct);
        return Message.removed(ResourceType.PRODUCTS);
    }
}
