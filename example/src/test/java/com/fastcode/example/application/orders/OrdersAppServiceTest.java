package com.fastcode.example.application.orders;

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

import com.fastcode.example.domain.orders.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.orders.dto.*;
import com.fastcode.example.domain.orders.QOrders;
import com.fastcode.example.domain.orders.Orders;

import com.fastcode.example.domain.customers.Customers;
import com.fastcode.example.domain.customers.ICustomersRepository;
import com.fastcode.example.domain.orderitems.Orderitems;
import com.fastcode.example.domain.orderitems.IOrderitemsRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrdersAppServiceTest {

	@InjectMocks
	@Spy
	protected OrdersAppService _appService;
	@Mock
	protected IOrdersRepository _ordersRepository;
	
    @Mock
	protected ICustomersRepository _customersRepository;

	@Mock
	protected IOrdersMapper _mapper;

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
	public void findOrdersById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Orders> nullOptional = Optional.ofNullable(null);
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findOrdersById_IdIsNotNullAndIdExists_ReturnOrders() {

		Orders orders = mock(Orders.class);
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.ordersToFindOrdersByIdOutput(orders));
	}
	
	
	@Test 
    public void createOrders_OrdersIsNotNullAndOrdersDoesNotExist_StoreOrders() { 
 
        Orders ordersEntity = mock(Orders.class); 
    	CreateOrdersInput ordersInput = new CreateOrdersInput();
		
        Customers customers = mock(Customers.class);
		Optional<Customers> customersOptional = Optional.of((Customers) customers);
        ordersInput.setCustomerId(15);
		
        Mockito.when(_customersRepository.findById(any(Integer.class))).thenReturn(customersOptional);
        
		
        Mockito.when(_mapper.createOrdersInputToOrders(any(CreateOrdersInput.class))).thenReturn(ordersEntity); 
        Mockito.when(_ordersRepository.save(any(Orders.class))).thenReturn(ordersEntity);

	   	Assertions.assertThat(_appService.create(ordersInput)).isEqualTo(_mapper.ordersToCreateOrdersOutput(ordersEntity));

    } 
    @Test
	public void createOrders_OrdersIsNotNullAndOrdersDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreOrders() {

		Orders orders = mock(Orders.class);
		CreateOrdersInput ordersInput = mock(CreateOrdersInput.class);
		
		
		Mockito.when(_mapper.createOrdersInputToOrders(any(CreateOrdersInput.class))).thenReturn(orders);
		Mockito.when(_ordersRepository.save(any(Orders.class))).thenReturn(orders);
	    Assertions.assertThat(_appService.create(ordersInput)).isEqualTo(_mapper.ordersToCreateOrdersOutput(orders)); 
	}
	
    @Test
	public void updateOrders_OrdersIsNotNullAndOrdersDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedOrders() {

		Orders orders = mock(Orders.class);
		UpdateOrdersInput ordersInput = mock(UpdateOrdersInput.class);
		
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
		
		Mockito.when(_mapper.updateOrdersInputToOrders(any(UpdateOrdersInput.class))).thenReturn(orders);
		Mockito.when(_ordersRepository.save(any(Orders.class))).thenReturn(orders);
		Assertions.assertThat(_appService.update(ID,ordersInput)).isEqualTo(_mapper.ordersToUpdateOrdersOutput(orders));
	}
	
		
	@Test
	public void updateOrders_OrdersIdIsNotNullAndIdExists_ReturnUpdatedOrders() {

		Orders ordersEntity = mock(Orders.class);
		UpdateOrdersInput orders= mock(UpdateOrdersInput.class);
		
		Optional<Orders> ordersOptional = Optional.of((Orders) ordersEntity);
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
	 		
		Mockito.when(_mapper.updateOrdersInputToOrders(any(UpdateOrdersInput.class))).thenReturn(ordersEntity);
		Mockito.when(_ordersRepository.save(any(Orders.class))).thenReturn(ordersEntity);
		Assertions.assertThat(_appService.update(ID,orders)).isEqualTo(_mapper.ordersToUpdateOrdersOutput(ordersEntity));
	}
    
	@Test
	public void deleteOrders_OrdersIsNotNullAndOrdersExists_OrdersRemoved() {

		Orders orders = mock(Orders.class);
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
 		
		_appService.delete(ID); 
		verify(_ordersRepository).delete(orders);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Orders> list = new ArrayList<>();
		Page<Orders> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrdersByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_ordersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Orders> list = new ArrayList<>();
		Orders orders = mock(Orders.class);
		list.add(orders);
    	Page<Orders> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrdersByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.ordersToFindOrdersByIdOutput(orders));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_ordersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QOrders orders = QOrders.ordersEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("status",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(orders.status.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(orders,map,searchMap)).isEqualTo(builder);
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
        list.add("status");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QOrders orders = QOrders.ordersEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("status");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(orders.status.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QOrders.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Customers
	@Test
	public void GetCustomers_IfOrdersIdAndCustomersIdIsNotNullAndOrdersExists_ReturnCustomers() {
		Orders orders = mock(Orders.class);
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
		Customers customersEntity = mock(Customers.class);

		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);

		Mockito.when(orders.getCustomers()).thenReturn(customersEntity);
		Assertions.assertThat(_appService.getCustomers(ID)).isEqualTo(_mapper.customersToGetCustomersOutput(customersEntity, orders));
	}

	@Test 
	public void GetCustomers_IfOrdersIdAndCustomersIdIsNotNullAndOrdersDoesNotExist_ReturnNull() {
		Optional<Orders> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getCustomers(ID)).isEqualTo(null);
	}

	@Test
	public void ParseorderitemsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("orderId", keyString);
		Assertions.assertThat(_appService.parseOrderitemsJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}