package com.finalyearproject.fyp.controller;


import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.service.serviceInterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    //DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
