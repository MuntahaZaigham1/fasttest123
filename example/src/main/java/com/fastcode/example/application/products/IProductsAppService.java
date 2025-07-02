package com.fastcode.example.application.products;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.products.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface IProductsAppService {
	
	//CRUD Operations
	CreateProductsOutput create(CreateProductsInput products);

    void delete(Integer id);

    UpdateProductsOutput update(Integer id, UpdateProductsInput input);

    FindProductsByIdOutput findById(Integer id);


    List<FindProductsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseOrderitemsJoinColumn(String keysString);

	Map<String,String> parseProductsuppliersJoinColumn(String keysString);
}
