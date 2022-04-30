package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Product")
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){this.productService = productService;

    }

    //PRODUCT ========================================================================================================
    @PostMapping("/")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long id ){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product/name/{productName}")
    public ResponseEntity<List<ProductResponseDTO>> getProductByName(@PathVariable("productName") String productName){
        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @GetMapping("/products/between/{price1}/and/{price2}")
    public ResponseEntity <List<ProductResponseDTO>> getProductByPriceRange(@PathVariable("price1") int price, @PathVariable("price2") int price2){
        return ResponseEntity.ok(productService.getProductByPriceRange(price, price2));
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable("id") Long productId){
        return productService.deleteProduct(productId);
    }

    //CATEGORY================================================================================================================
    @GetMapping("/product/{productId}/category")
    public ResponseEntity <Set<CategoryResponseDTO>> getCategory(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.getCategory(productId));
    }

    @PostMapping("/add/category/{categoryId}/to/product/{productId}")
    public ResponseEntity<String> addCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.addCategory(productId,categoryId));
    }

    @PostMapping("/remove/category/{categoryId}/from/product/{productId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.removeCategory(productId,categoryId));
    }

    @PostMapping("/add/categories/to/product/{productId}")
    public ResponseEntity<String> addCategories(@RequestBody List<Long> categoryIds, @PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.addCategories(productId,categoryIds));
    }

    @PostMapping("/remove/categories/from/product/{productId}")
    public ResponseEntity<String> removeCategories(@RequestBody List<Long> categoryIds, @PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.removeCategories(productId,categoryIds));
    }

    //INVENTORY===============================================================================================
    @GetMapping("/product/inventory/{productId}")
    public ResponseEntity<Integer> getProductInventory(@PathVariable("productId")Long productId){
        return ResponseEntity.ok(productService.getProductInventory(productId));
    }

    @PostMapping("/add/inventory/{quantity}/to/product/{productId}")
    public ResponseEntity<Integer> addInventoryQuantity(@PathVariable("productId") Long productId,
                                                                   @PathVariable("quantity") Integer quantity){
        return ResponseEntity.ok(productService.addInventoryQuantity(productId,quantity));
    }

    @PostMapping("/reduce/inventory/{quantity}/from/product/{productId}")
    public ResponseEntity<Integer> reduceInventoryQuantity(@PathVariable("productId") Long productId,
                                                       @PathVariable("quantity") Integer quantity){
        return ResponseEntity.ok(productService.reduceInventoryQuantity(productId,quantity));
    }

    @PostMapping("/set/product/{productId}/to/inventory/{quantity}")
    public ResponseEntity<Integer> setInventoryQuantity(@PathVariable("productId") Long productId,
                                                        @PathVariable("quantity") Integer quantity){
        return ResponseEntity.ok(productService.setInventoryQuantity(productId,quantity));
    }

}
