package com.fastcode.example.application.customers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.customers.dto.*;
import com.fastcode.example.domain.customers.ICustomersRepository;
import com.fastcode.example.domain.customers.QCustomers;
import com.fastcode.example.domain.customers.Customers;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("customersAppService")
@RequiredArgsConstructor
public class CustomersAppService implements ICustomersAppService {
    
	@Qualifier("customersRepository")
	@NonNull protected final ICustomersRepository _customersRepository;

	
	@Qualifier("ICustomersMapperImpl")
	@NonNull protected final ICustomersMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateCustomersOutput create(CreateCustomersInput input) {

		Customers customers = mapper.createCustomersInputToCustomers(input);

		Customers createdCustomers = _customersRepository.save(customers);
		return mapper.customersToCreateCustomersOutput(createdCustomers);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateCustomersOutput update(Integer customersId, UpdateCustomersInput input) {

		Customers existing = _customersRepository.findById(customersId).orElseThrow(() -> new EntityNotFoundException("Customers not found"));

		Customers customers = mapper.updateCustomersInputToCustomers(input);
		customers.setOrdersSet(existing.getOrdersSet());
		
		Customers updatedCustomers = _customersRepository.save(customers);
		return mapper.customersToUpdateCustomersOutput(updatedCustomers);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer customersId) {

		Customers existing = _customersRepository.findById(customersId).orElseThrow(() -> new EntityNotFoundException("Customers not found"));
	 	
        if(existing !=null) {
			_customersRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindCustomersByIdOutput findById(Integer customersId) {

		Customers foundCustomers = _customersRepository.findById(customersId).orElse(null);
		if (foundCustomers == null)  
			return null; 
 	   
 	    return mapper.customersToFindCustomersByIdOutput(foundCustomers);
	}

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindCustomersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Customers> foundCustomers = _customersRepository.findAll(search(search), pageable);
		List<Customers> customersList = foundCustomers.getContent();
		Iterator<Customers> customersIterator = customersList.iterator(); 
		List<FindCustomersByIdOutput> output = new ArrayList<>();

		while (customersIterator.hasNext()) {
		Customers customers = customersIterator.next();
 	    output.add(mapper.customersToFindCustomersByIdOutput(customers));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QCustomers customers= QCustomers.customersEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(customers, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("customerId") ||
				list.get(i).replace("%20","").trim().equals("email") ||
				list.get(i).replace("%20","").trim().equals("name")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QCustomers customers, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(customers.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(customers.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(customers.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(customers.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(customers.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("customerId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(customers.customerId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(customers.customerId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(customers.customerId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(customers.customerId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(customers.customerId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(customers.customerId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("name")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(customers.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(customers.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(customers.name.ne(details.getValue().getSearchValue()));
				}
			}
	    
		}
		
		return builder;
	}
	
	public Map<String,String> parseOrdersJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("customerId", keysString);
		  
		return joinColumnMap;
	}
    
}


