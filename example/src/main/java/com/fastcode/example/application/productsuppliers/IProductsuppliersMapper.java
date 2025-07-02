package com.fastcode.example.application.productsuppliers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.suppliers.Suppliers;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProductsuppliersMapper {
   Productsuppliers createProductsuppliersInputToProductsuppliers(CreateProductsuppliersInput productsuppliersDto);
   
   @Mappings({ 
   @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   @Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   }) 
   CreateProductsuppliersOutput productsuppliersToCreateProductsuppliersOutput(Productsuppliers entity);
   
    Productsuppliers updateProductsuppliersInputToProductsuppliers(UpdateProductsuppliersInput productsuppliersDto);
    
    @Mappings({ 
    @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
    @Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   	}) 
   	UpdateProductsuppliersOutput productsuppliersToUpdateProductsuppliersOutput(Productsuppliers entity);
   	@Mappings({ 
   	@Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	@Mapping(source = "entity.suppliers.supplierId", target = "suppliersDescriptiveField"),                    
   	}) 
   	FindProductsuppliersByIdOutput productsuppliersToFindProductsuppliersByIdOutput(Productsuppliers entity);


   @Mappings({
   @Mapping(source = "products.productId", target = "productId"),                  
   @Mapping(source = "foundProductsuppliers.productId", target = "productsuppliersProductId"),
   @Mapping(source = "foundProductsuppliers.supplierId", target = "productsuppliersSupplierId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, Productsuppliers foundProductsuppliers);
   
   @Mappings({
   @Mapping(source = "suppliers.supplierId", target = "supplierId"),                  
   @Mapping(source = "foundProductsuppliers.productId", target = "productsuppliersProductId"),
   @Mapping(source = "foundProductsuppliers.supplierId", target = "productsuppliersSupplierId"),
   })
   GetSuppliersOutput suppliersToGetSuppliersOutput(Suppliers suppliers, Productsuppliers foundProductsuppliers);
   
}
