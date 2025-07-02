package com.fastcode.example.application.orders;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.orders.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface IOrdersAppService {
	
	//CRUD Operations
	CreateOrdersOutput create(CreateOrdersInput orders);

    void delete(Integer id);

    UpdateOrdersOutput update(Integer id, UpdateOrdersInput input);

    FindOrdersByIdOutput findById(Integer id);


    List<FindOrdersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
    
    GetCustomersOutput getCustomers(Integer ordersid);
    
    //Join Column Parsers

	Map<String,String> parseOrderitemsJoinColumn(String keysString);
}
