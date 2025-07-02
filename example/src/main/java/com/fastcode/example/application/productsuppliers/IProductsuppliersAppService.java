package com.fastcode.example.application.productsuppliers;

import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface IProductsuppliersAppService {
	
	//CRUD Operations
	CreateProductsuppliersOutput create(CreateProductsuppliersInput productsuppliers);

    void delete(ProductsuppliersId productsuppliersId);

    UpdateProductsuppliersOutput update(ProductsuppliersId productsuppliersId, UpdateProductsuppliersInput input);

    FindProductsuppliersByIdOutput findById(ProductsuppliersId productsuppliersId);


    List<FindProductsuppliersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetProductsOutput getProducts(ProductsuppliersId productsuppliersId);
    
    GetSuppliersOutput getSuppliers(ProductsuppliersId productsuppliersId);
    
    //Join Column Parsers
    
	ProductsuppliersId parseProductsuppliersKey(String keysString);
}
