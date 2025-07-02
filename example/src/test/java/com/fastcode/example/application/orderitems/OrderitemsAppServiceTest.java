package com.fastcode.example.application.orderitems;

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

import com.fastcode.example.domain.orderitems.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.domain.orderitems.QOrderitems;
import com.fastcode.example.domain.orderitems.Orderitems;

import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderitemsAppServiceTest {

	@InjectMocks
	@Spy
	protected OrderitemsAppService _appService;
	@Mock
	protected IOrderitemsRepository _orderitemsRepository;
	
    @Mock
	protected IOrdersRepository _ordersRepository;

    @Mock
	protected IProductsRepository _productsRepository;

	@Mock
	protected IOrderitemsMapper _mapper;

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
	public void findOrderitemsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Orderitems> nullOptional = Optional.ofNullable(null);
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findOrderitemsById_IdIsNotNullAndIdExists_ReturnOrderitems() {

		Orderitems orderitems = mock(Orderitems.class);
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitems);
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.orderitemsToFindOrderitemsByIdOutput(orderitems));
	}
	
	
	@Test 
    public void createOrderitems_OrderitemsIsNotNullAndOrderitemsDoesNotExist_StoreOrderitems() { 
 
        Orderitems orderitemsEntity = mock(Orderitems.class); 
    	CreateOrderitemsInput orderitemsInput = new CreateOrderitemsInput();
		
        Orders orders = mock(Orders.class);
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
        orderitemsInput.setOrderId(15);
		
        Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
        
		
        Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
        orderitemsInput.setProductId(15);
		
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
        
		
        Mockito.when(_mapper.createOrderitemsInputToOrderitems(any(CreateOrderitemsInput.class))).thenReturn(orderitemsEntity); 
        Mockito.when(_orderitemsRepository.save(any(Orderitems.class))).thenReturn(orderitemsEntity);

	   	Assertions.assertThat(_appService.create(orderitemsInput)).isEqualTo(_mapper.orderitemsToCreateOrderitemsOutput(orderitemsEntity));

    } 
    @Test
	public void createOrderitems_OrderitemsIsNotNullAndOrderitemsDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreOrderitems() {

		Orderitems orderitems = mock(Orderitems.class);
		CreateOrderitemsInput orderitemsInput = mock(CreateOrderitemsInput.class);
		
		
		Mockito.when(_mapper.createOrderitemsInputToOrderitems(any(CreateOrderitemsInput.class))).thenReturn(orderitems);
		Mockito.when(_orderitemsRepository.save(any(Orderitems.class))).thenReturn(orderitems);
	    Assertions.assertThat(_appService.create(orderitemsInput)).isEqualTo(_mapper.orderitemsToCreateOrderitemsOutput(orderitems)); 
	}
	
    @Test
	public void updateOrderitems_OrderitemsIsNotNullAndOrderitemsDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedOrderitems() {

		Orderitems orderitems = mock(Orderitems.class);
		UpdateOrderitemsInput orderitemsInput = mock(UpdateOrderitemsInput.class);
		
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitems);
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);
		
		Mockito.when(_mapper.updateOrderitemsInputToOrderitems(any(UpdateOrderitemsInput.class))).thenReturn(orderitems);
		Mockito.when(_orderitemsRepository.save(any(Orderitems.class))).thenReturn(orderitems);
		Assertions.assertThat(_appService.update(ID,orderitemsInput)).isEqualTo(_mapper.orderitemsToUpdateOrderitemsOutput(orderitems));
	}
	
		
	@Test
	public void updateOrderitems_OrderitemsIdIsNotNullAndIdExists_ReturnUpdatedOrderitems() {

		Orderitems orderitemsEntity = mock(Orderitems.class);
		UpdateOrderitemsInput orderitems= mock(UpdateOrderitemsInput.class);
		
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitemsEntity);
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);
	 		
		Mockito.when(_mapper.updateOrderitemsInputToOrderitems(any(UpdateOrderitemsInput.class))).thenReturn(orderitemsEntity);
		Mockito.when(_orderitemsRepository.save(any(Orderitems.class))).thenReturn(orderitemsEntity);
		Assertions.assertThat(_appService.update(ID,orderitems)).isEqualTo(_mapper.orderitemsToUpdateOrderitemsOutput(orderitemsEntity));
	}
    
	@Test
	public void deleteOrderitems_OrderitemsIsNotNullAndOrderitemsExists_OrderitemsRemoved() {

		Orderitems orderitems = mock(Orderitems.class);
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitems);
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);
 		
		_appService.delete(ID); 
		verify(_orderitemsRepository).delete(orderitems);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Orderitems> list = new ArrayList<>();
		Page<Orderitems> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrderitemsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_orderitemsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Orderitems> list = new ArrayList<>();
		Orderitems orderitems = mock(Orderitems.class);
		list.add(orderitems);
    	Page<Orderitems> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrderitemsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.orderitemsToFindOrderitemsByIdOutput(orderitems));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_orderitemsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QOrderitems orderitems = QOrderitems.orderitemsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(orderitems,map,searchMap)).isEqualTo(builder);
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
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QOrderitems orderitems = QOrderitems.orderitemsEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QOrderitems.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Orders
	@Test
	public void GetOrders_IfOrderitemsIdAndOrdersIdIsNotNullAndOrderitemsExists_ReturnOrders() {
		Orderitems orderitems = mock(Orderitems.class);
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitems);
		Orders ordersEntity = mock(Orders.class);

		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);

		Mockito.when(orderitems.getOrders()).thenReturn(ordersEntity);
		Assertions.assertThat(_appService.getOrders(ID)).isEqualTo(_mapper.ordersToGetOrdersOutput(ordersEntity, orderitems));
	}

	@Test 
	public void GetOrders_IfOrderitemsIdAndOrdersIdIsNotNullAndOrderitemsDoesNotExist_ReturnNull() {
		Optional<Orderitems> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getOrders(ID)).isEqualTo(null);
	}
   
    //Products
	@Test
	public void GetProducts_IfOrderitemsIdAndProductsIdIsNotNullAndOrderitemsExists_ReturnProducts() {
		Orderitems orderitems = mock(Orderitems.class);
		Optional<Orderitems> orderitemsOptional = Optional.of((Orderitems) orderitems);
		Products productsEntity = mock(Products.class);

		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(orderitemsOptional);

		Mockito.when(orderitems.getProducts()).thenReturn(productsEntity);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(_mapper.productsToGetProductsOutput(productsEntity, orderitems));
	}

	@Test 
	public void GetProducts_IfOrderitemsIdAndProductsIdIsNotNullAndOrderitemsDoesNotExist_ReturnNull() {
		Optional<Orderitems> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_orderitemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(null);
	}

}