package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.MyUtils;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.CategoryMapper;
import com.finalyearproject.fyp.mapper.ProductMapper;
import com.finalyearproject.fyp.model.Category;
import com.finalyearproject.fyp.model.Product;
import com.finalyearproject.fyp.repository.ProductRepository;
import com.finalyearproject.fyp.service.serviceInterface.CategoryService;
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
class ProductServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    //PRODUCT===========================================================================================================================
    @Transactional
    @Override
    @Caching(
            put = {@CachePut(value = "product", key = "#result.id")},
            evict = {@CacheEvict(value = "allproduct", allEntries = true)}
    )
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO);
        return productMapper.productToProductResponseDTO(productRepository.save(product));
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.PRODUCT, productId)));
    }

    @Override
    @Cacheable(value = "product", key = "#productId ")
    public ProductResponseDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productToProductResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Message.resourceNotFound(ResourceType.PRODUCT, productId)));
    }

    @Override
    @Cacheable(value = "allproduct", unless = "#result.size() > 100")
    public List<ProductResponseDTO> getAllProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct.stream().map(productMapper::productToProductResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getProductByName(String productName) {
        return productRepository.findByNameIgnoreCase(productName)
                .stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductResponseDTO> getProductByPriceRange(int price1, int price2) {
        // TODO: 4/27/2022 Use Query to get this result
        return productRepository.findByPriceBetween(price1, price2)
                .stream()
                .map(productMapper::productToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "allproduct", allEntries = true)})
    public String deleteProduct(Long id) {
        this.getProduct(id);
        productRepository.deleteById(id);
        return Message.deleted(ResourceType.PRODUCT);
    }

    //CATEGORY===================================================================================================================
    @Override
    @Cacheable(value = "productcategory", key = "#productId")
    public Set<CategoryResponseDTO> getCategory(Long productId) {
        return this.getProduct(productId)
                .getCategory()
                .stream().map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    @CacheEvict(value = "productcategory", key = "#productId")
    public String addCategory(Long productId, Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        Product product = this.getProduct(productId);
        product.addCategory(category);
        productRepository.save(product);
        return Message.added(ResourceType.CATEGORY);
    }

    @Transactional
    @Override
    @CacheEvict(value = "productcategory", key = "#productId")
    public String addCategories(Long productId, List<Long> categoryId) {
        if (!categoryId.isEmpty()) {
            categoryId.forEach(id -> addCategory(productId, id));
            return Message.added(ResourceType.CATEGORIES);
        } else {
            return Message.isEmpty(ResourceType.CATEGORY.toString());
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = "productcategory", key = "#productId")
    public String removeCategory(Long productId, Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        Product product = this.getProduct(productId);
        product.removeCategory(category);
        productRepository.save(product);
        return Message.removed(ResourceType.CATEGORY);
    }

    @Transactional
    @Override
    @CacheEvict(value = "productcategory", key = "#productId")
    public String removeCategories(Long productId, List<Long> categoryId) {
        if (!categoryId.isEmpty()) {
            categoryId.forEach(id -> removeCategory(productId, id));
            return Message.removed(ResourceType.CATEGORIES);
        } else {
            return Message.isEmpty(ResourceType.CATEGORY.toString());
        }
    }

    //INVENTORY=========================================================================================================================
    @Override
    public Integer getProductInventory(Long productId) {
        return this.getProduct(productId).getInventoryQuantity();
    }

    @Transactional
    @Override
    public Integer addInventoryQuantity(Long productId, int quantity) {
        Product product = this.getProduct(productId);
        int newQuantity = product.addInventoryQuantityBy(quantity);
        productRepository.save(product);
        return newQuantity;
    }

    @Transactional
    @Override
    public Integer setInventoryQuantity(Long productId, int quantity) {
        Product product = this.getProduct(productId);
        product.setInventoryQuantityBy(quantity);
        productRepository.save(product);
        return quantity;
    }

    @Transactional
    @Override
    public Integer reduceInventoryQuantity(Long productId, int quantity) {
        Product product = this.getProduct(productId);
        int newQuantity = product.subtractInventoryQuantityBy(quantity);
        productRepository.save(product);
        return newQuantity;
    }

    // UPDATE PRODUCT ==================================================================================================================
    @Transactional
    @Override
    @Caching(
            put = {@CachePut(value = "product", key = "#productId")},
            evict = {@CacheEvict(value = "allproduct", allEntries = true)}
    )
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {

        Product update = getProduct(productId);

        if (MyUtils.isNotEmptyAndNotNull(productRequestDTO.getName())) {
            update.setName(productRequestDTO.getName());
        }
        if (productRequestDTO.getPrice() > 0) {
            update.setPrice(productRequestDTO.getPrice());
        }
        if (MyUtils.isNotEmptyAndNotNull(productRequestDTO.getDescription())) {
            update.setDescription(productRequestDTO.getDescription());
        }
        if (MyUtils.isNotEmptyAndNotNull(productRequestDTO.getImageUrl())) {
            update.setImageUrl(productRequestDTO.getImageUrl());
        }
        if (productRequestDTO.getCost() > 0) {
            update.setCost(productRequestDTO.getCost());
        }
        if (productRequestDTO.getInventoryQuantity() > 0) {
            update.setInventoryQuantityBy(productRequestDTO.getInventoryQuantity());
        }
        /*
         No updates for category here because of its ambiguity.
         * Should the given category me added or removed?
         * Either case it is best to use the add/delete category methods to perform those actions as the case may be
         * */
        productRepository.save(update);

        System.out.println(Message.updated(ResourceType.PRODUCT));// TODO: 4/30/2022 Use logs

        return productMapper.productToProductResponseDTO(update);
    }

}