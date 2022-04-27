package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Request.CategoryRequestDTO;
import com.finalyearproject.fyp.dto.Request.CategoryRequestDTOWithProduct;
import com.finalyearproject.fyp.dto.Response.CategoryResponseDTO;
import com.finalyearproject.fyp.model.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {

    Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO);
//    default Category CategoryRequestDTOWithProductToCategory(CategoryRequestDTOWithProduct categoryRequestDTOWithProduct){
//        if ( categoryRequestDTOWithProduct == null ) {
//            return null;
//        }
//
//        Category category = new Category();
//
//        category.setName( categoryRequestDTOWithProduct.getName() );
//        category.setDescription( categoryRequestDTOWithProduct.getDescription() );
//        category.setImageUrl( categoryRequestDTOWithProduct.getImageUrl() );
//
//        return category;
//    }



    CategoryResponseDTO categoryToCategoryResponseDTO(Category category);
}
