package com.fastcode.example.application.customers;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.customers.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface ICustomersAppService {
	
	//CRUD Operations
	CreateCustomersOutput create(CreateCustomersInput customers);

    void delete(Integer id);

    UpdateCustomersOutput update(Integer id, UpdateCustomersInput input);

    FindCustomersByIdOutput findById(Integer id);


    List<FindCustomersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseOrdersJoinColumn(String keysString);
}
