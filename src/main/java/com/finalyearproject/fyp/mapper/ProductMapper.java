package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.dto.Response.ProductResponseDTO;
import com.finalyearproject.fyp.model.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

    ProductResponseDTO productToProductResponseDTO(Product product);

    Product productRequestDTOToProduct(ProductRequestDTO productRequestDTO);

}
