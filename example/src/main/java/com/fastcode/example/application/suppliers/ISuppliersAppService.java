package com.fastcode.example.application.suppliers;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.suppliers.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface ISuppliersAppService {
	
	//CRUD Operations
	CreateSuppliersOutput create(CreateSuppliersInput suppliers);

    void delete(Integer id);

    UpdateSuppliersOutput update(Integer id, UpdateSuppliersInput input);

    FindSuppliersByIdOutput findById(Integer id);


    List<FindSuppliersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseProductsuppliersJoinColumn(String keysString);
}
