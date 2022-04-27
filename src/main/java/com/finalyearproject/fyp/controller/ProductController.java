package com.finalyearproject.fyp.controller;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){this.productService = productService;

    }

    //CREATE NEW PRODUCT
    @PostMapping("/")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }
    //GET REQUEST
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

    @GetMapping("/product/inventory/{productId}")
    public ResponseEntity<Integer> getProductInventory(@PathVariable("productId")@NotNull Long productId){
        return ResponseEntity.ok(productService.getProductInventory(productId));
    }

    @GetMapping("/products/between/{price1}/and/{price2}")
    public ResponseEntity <List<ProductResponseDTO>> getProductByPriceRange(@PathVariable("price1") @NotNull int price,
                                                                            @PathVariable("price2") @NotNull int price2){
        return ResponseEntity.ok(productService.getProductByPriceRange(price, price2));
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }
    //DELETE PRODUCT
    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable("id") Long productId){
        return productService.deleteProduct(productId);
    }

}
