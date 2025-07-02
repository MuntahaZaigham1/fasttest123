package com.fastcode.example.application.orderitems;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface IOrderitemsAppService {
	
	//CRUD Operations
	CreateOrderitemsOutput create(CreateOrderitemsInput orderitems);

    void delete(Integer id);

    UpdateOrderitemsOutput update(Integer id, UpdateOrderitemsInput input);

    FindOrderitemsByIdOutput findById(Integer id);


    List<FindOrderitemsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetOrdersOutput getOrders(Integer orderitemsid);
    
    GetProductsOutput getProducts(Integer orderitemsid);
    
    //Join Column Parsers
}
