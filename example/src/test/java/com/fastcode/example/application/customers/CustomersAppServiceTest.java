package com.fastcode.example.application.customers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastcode.example.domain.customers.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.customers.dto.*;
import com.fastcode.example.domain.customers.QCustomers;
import com.fastcode.example.domain.customers.Customers;

import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomersAppServiceTest {

	@InjectMocks
	@Spy
	protected CustomersAppService _appService;
	@Mock
	protected ICustomersRepository _customersRepository;
	
	@Mock
	protected ICustomersMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    protected static Integer ID=15;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findCustomersById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Customers> nullOptional = Optional.ofNullable(null);
		Mockito.when(_customersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findCustomersById_IdIsNotNullAndIdExists_ReturnCustomers() {

		Customers customers = mock(Customers.class);
		Optional<Customers> customersOptional = Optional.of((Customers) customers);
		Mockito.when(_customersRepository.findById(any(Integer.class))).thenReturn(customersOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.customersToFindCustomersByIdOutput(customers));
	}
	
	
	@Test 
    public void createCustomers_CustomersIsNotNullAndCustomersDoesNotExist_StoreCustomers() { 
 
        Customers customersEntity = mock(Customers.class); 
    	CreateCustomersInput customersInput = new CreateCustomersInput();
		
        Mockito.when(_mapper.createCustomersInputToCustomers(any(CreateCustomersInput.class))).thenReturn(customersEntity); 
        Mockito.when(_customersRepository.save(any(Customers.class))).thenReturn(customersEntity);

	   	Assertions.assertThat(_appService.create(customersInput)).isEqualTo(_mapper.customersToCreateCustomersOutput(customersEntity));

    } 
	@Test
	public void updateCustomers_CustomersIdIsNotNullAndIdExists_ReturnUpdatedCustomers() {

		Customers customersEntity = mock(Customers.class);
		UpdateCustomersInput customers= mock(UpdateCustomersInput.class);
		
		Optional<Customers> customersOptional = Optional.of((Customers) customersEntity);
		Mockito.when(_customersRepository.findById(any(Integer.class))).thenReturn(customersOptional);
	 		
		Mockito.when(_mapper.updateCustomersInputToCustomers(any(UpdateCustomersInput.class))).thenReturn(customersEntity);
		Mockito.when(_customersRepository.save(any(Customers.class))).thenReturn(customersEntity);
		Assertions.assertThat(_appService.update(ID,customers)).isEqualTo(_mapper.customersToUpdateCustomersOutput(customersEntity));
	}
    
	@Test
	public void deleteCustomers_CustomersIsNotNullAndCustomersExists_CustomersRemoved() {

		Customers customers = mock(Customers.class);
		Optional<Customers> customersOptional = Optional.of((Customers) customers);
		Mockito.when(_customersRepository.findById(any(Integer.class))).thenReturn(customersOptional);
 		
		_appService.delete(ID); 
		verify(_customersRepository).delete(customers);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Customers> list = new ArrayList<>();
		Page<Customers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindCustomersByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_customersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Customers> list = new ArrayList<>();
		Customers customers = mock(Customers.class);
		list.add(customers);
    	Page<Customers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindCustomersByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.customersToFindCustomersByIdOutput(customers));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_customersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QCustomers customers = QCustomers.customersEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("email",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(customers.email.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(customers,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
        list.add("email");
        list.add("name");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QCustomers customers = QCustomers.customersEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("email");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(customers.email.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QCustomers.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

	@Test
	public void ParseordersJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("customerId", keyString);
		Assertions.assertThat(_appService.parseOrdersJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}