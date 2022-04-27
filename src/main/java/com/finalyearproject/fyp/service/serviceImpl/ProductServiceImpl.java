package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.ProductMapper;
import com.finalyearproject.fyp.model.Category;
import com.finalyearproject.fyp.model.Product;
import com.finalyearproject.fyp.repository.ProductRepository;
import com.finalyearproject.fyp.service.serviceInterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ProductServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.productRequestDTOToProduct(productRequestDTO);
        return productMapper.productToProductResponseDTO(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> getAllProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct.stream().map(productMapper::productToProductResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productToProductResponseDTO)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.PRODUCT, productId)));
    }

    @Override
    public int getProductInventory(Long productId) {
        return getProduct(productId).getInventoryQuantity();
    }

    @Transactional
    @Override
    public String deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.PRODUCT, id)));
        productRepository.deleteById(id);
        return Message.deleted(ResourceType.PRODUCT);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.PRODUCT, productId)));
    }

    @Override
    public String addCategory(Long productId, Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        getProduct(productId).addCategory(category);
        return Message.added(ResourceType.CATEGORY);
    }

    @Override
    public String removeCategory(Long productId, Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        getProduct(productId).removeCategory(category);
        return Message.removed(ResourceType.CATEGORY);
    }

    @Override
    public String setInventoryQuantity(Long productId, int quantity) {
        getProduct(productId).setInventoryQuantityBy(quantity);
        return "QUANTITY " + Message.updated;
    }

    @Override
    public String addCategories(Long productId, List<Long> categoryId) {
        if(!categoryId.isEmpty()){
            categoryId.forEach(id ->addCategory(productId,id));
        }
        return Message.added(ResourceType.CATEGORIES);
    }

    @Override
    public String removeCategories(Long productId, List<Long> categoryId) {
        if(!categoryId.isEmpty()){
            categoryId.forEach(id ->removeCategory(productId,id));
        }
        return Message.removed(ResourceType.CATEGORIES);
    }

    @Transactional
    @Override
    public String addInventoryQuantity(Long productId, int quantity) {
        getProduct(productId).addInventoryQuantityBy(quantity);
        return "QUANTITY " + Message.updated;
    }

    @Override
    public String reduceInventoryQuantity(Long productId, int quantity) {
        getProduct(productId).subtractInventoryQuantityBy(quantity);
        return "QUANTITY " + Message.updated;
    }

    @Override
    public List<ProductResponseDTO> getProductByName(String productName) {
        return productRepository.findByNameIgnoreCase(productName)
                .stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductResponseDTO> getProductByPriceRange(int price) {
        return null;// TODO: 4/27/2022 Use Query to get this result
    }

    @Transactional
    @Override
    public String updateProduct(Long productId, ProductRequestDTO productRequestDTO) {

        Product toBeUpdated = getProduct(productId);

        toBeUpdated.setName(productRequestDTO.getName());
        toBeUpdated.setPrice(productRequestDTO.getPrice());
        toBeUpdated.setDescription(productRequestDTO.getDescription());
        toBeUpdated.setImageUrl(productRequestDTO.getImageUrl());
        toBeUpdated.setCost(productRequestDTO.getCost());
        toBeUpdated.setInventoryQuantityBy(productRequestDTO.getInventoryQuantity());
        /*
         * No updates for category here because of its ambiguity.
         * Should the given category me added or removed?
         * Either case it is best to use the add/remove category methods to perform those actions as the case may be
         * */
        productRepository.save(toBeUpdated);

        return Message.updated(ResourceType.PRODUCT);
    }

}