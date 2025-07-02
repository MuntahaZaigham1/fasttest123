package com.fastcode.example.application.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.application.products.dto.*;
import com.fastcode.example.domain.products.Products;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProductsMapper {
   Products createProductsInputToProducts(CreateProductsInput productsDto);
   CreateProductsOutput productsToCreateProductsOutput(Products entity);
   
    Products updateProductsInputToProducts(UpdateProductsInput productsDto);
    
   	UpdateProductsOutput productsToUpdateProductsOutput(Products entity);
   	FindProductsByIdOutput productsToFindProductsByIdOutput(Products entity);


}
