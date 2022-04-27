package com.finalyearproject.fyp.controller;


import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //CREATE NEW CATEGORY
    @PostMapping("/")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequestDTO), HttpStatus.CREATED);
    }

    //GET REQUESTS
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") Long id ){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoryByName(@PathVariable("name") String categoryName){
        return ResponseEntity.ok(categoryService.getCategoryByName(categoryName));
    }

    //UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@Valid @PathVariable("id") Long id,
                                             @RequestBody CategoryRequestDTO categoryRequestDTO){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryRequestDTO));
    }

    @PostMapping("/add/product/{productId}/to/category{categoryId}")
    public ResponseEntity<String> addProducts(@PathVariable @NotNull Long categoryId,
                                              @PathVariable @NotNull Long productId){

    }

    @PostMapping("/add/product/to/category{categoryId}")
    public ResponseEntity<String> addProducts(@PathVariable @NotNull Long categoryId,
                                              @RequestBody @NotNull List <Long> productId){

    }

    @PostMapping("/remove/product/{productId}/from/category{categoryId}")
    public ResponseEntity<String> removeProduct(@RequestBody @NotNull Long categoryId,
                                                @PathVariable@NotNull Long productId){

    }

    @PostMapping("/remove/products/from/category{categoryId}")
    public ResponseEntity<String> removeProducts(@RequestBody @NotNull Long categoryId,@NotNull Long productId){

    }

    //DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
