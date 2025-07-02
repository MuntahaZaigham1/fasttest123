package com.fastcode.example.restcontrollers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.application.orders.OrdersAppService;
import com.fastcode.example.application.orders.dto.*;
import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.domain.orders.Orders;

import com.fastcode.example.domain.customers.ICustomersRepository;
import com.fastcode.example.domain.customers.Customers;
import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.application.customers.CustomersAppService;
import com.fastcode.example.application.orderitems.OrderitemsAppService;
import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.TestConstants;
import com.fastcode.example.domain.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class OrdersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository orders_repository;
	
	@Autowired
	@Qualifier("customersRepository") 
	protected ICustomersRepository customersRepository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	

	@SpyBean
	@Qualifier("ordersAppService")
	protected OrdersAppService ordersAppService;
	
    @SpyBean
    @Qualifier("customersAppService")
	protected CustomersAppService  customersAppService;
	
    @SpyBean
    @Qualifier("orderitemsAppService")
	protected OrderitemsAppService  orderitemsAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Orders orders;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countCustomers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.orders CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.customers CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.orders CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	
	public Orders createOrdersEntity() {
	
		if(countOrders>60) {
			countOrders = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Orders ordersEntity = new Orders();
		
		ordersEntity.setOrderDate(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		ordersEntity.setOrderId(relationCount);
  		ordersEntity.setStatus(String.valueOf(relationCount));
		ordersEntity.setVersiono(0L);
		relationCount++;
		Customers customers= createCustomersEntity();
		ordersEntity.setCustomers(customers);
		if(!ordersRepository.findAll().contains(ordersEntity))
		{
			 ordersEntity = ordersRepository.save(ordersEntity);
		}
		countOrders++;
	    return ordersEntity;
	}
	public Customers createCustomersEntity() {
	
		if(countCustomers>60) {
			countCustomers = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Customers customersEntity = new Customers();
		
		customersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		customersEntity.setCustomerId(relationCount);
  		customersEntity.setEmail(String.valueOf(relationCount));
  		customersEntity.setName(String.valueOf(relationCount));
		customersEntity.setVersiono(0L);
		relationCount++;
		if(!customersRepository.findAll().contains(customersEntity))
		{
			 customersEntity = customersRepository.save(customersEntity);
		}
		countCustomers++;
	    return customersEntity;
	}

	public Orders createEntity() {
		Customers customers = createCustomersEntity();
	
		Orders ordersEntity = new Orders();
    	ordersEntity.setOrderDate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		ordersEntity.setOrderId(1);
		ordersEntity.setStatus("1");
		ordersEntity.setVersiono(0L);
		ordersEntity.setCustomers(customers);
		
		return ordersEntity;
	}
    public CreateOrdersInput createOrdersInput() {
	
	    CreateOrdersInput ordersInput = new CreateOrdersInput();
    	ordersInput.setOrderDate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		ordersInput.setStatus("5");
		
		return ordersInput;
	}

	public Orders createNewEntity() {
		Orders orders = new Orders();
    	orders.setOrderDate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		orders.setOrderId(3);
		orders.setStatus("3");
		
		return orders;
	}
	
	public Orders createUpdateEntity() {
		Orders orders = new Orders();
    	orders.setOrderDate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		orders.setOrderId(4);
		orders.setStatus("4");
		
		return orders;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final OrdersController ordersController = new OrdersController(ordersAppService, customersAppService, orderitemsAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(ordersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		orders= createEntity();
		List<Orders> list= orders_repository.findAll();
		if(!list.contains(orders)) {
			orders=orders_repository.save(orders);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/orders/" + orders.getOrderId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orders/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateOrders_OrdersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateOrdersInput ordersInput = createOrdersInput();	
		
	    
		Customers customers =  createCustomersEntity();

		ordersInput.setCustomerId(Integer.parseInt(customers.getCustomerId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(ordersInput);

		mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteOrders_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(ordersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/orders/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a orders with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Orders entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Customers customers = createCustomersEntity();
		entity.setCustomers(customers);
		entity = orders_repository.save(entity);
		

		FindOrdersByIdOutput output= new FindOrdersByIdOutput();
		output.setOrderId(entity.getOrderId());
		output.setStatus(entity.getStatus());
		
         Mockito.doReturn(output).when(ordersAppService).findById(entity.getOrderId());
       
    //    Mockito.when(ordersAppService.findById(entity.getOrderId())).thenReturn(output);
        
		mvc.perform(delete("/orders/" + entity.getOrderId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateOrders_OrdersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(ordersAppService).findById(999);
        
        UpdateOrdersInput orders = new UpdateOrdersInput();
		orders.setOrderDate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		orders.setOrderId(999);
  		orders.setStatus("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orders);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/orders/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Orders with id=999 not found."));
	}    

	@Test
	public void UpdateOrders_OrdersExists_ReturnStatusOk() throws Exception {
		Orders entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Customers customers = createCustomersEntity();
		entity.setCustomers(customers);
		entity = orders_repository.save(entity);
		FindOrdersByIdOutput output= new FindOrdersByIdOutput();
		output.setOrderDate(entity.getOrderDate());
		output.setOrderId(entity.getOrderId());
		output.setStatus(entity.getStatus());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(ordersAppService.findById(entity.getOrderId())).thenReturn(output);
        
        
		
		UpdateOrdersInput ordersInput = new UpdateOrdersInput();
		ordersInput.setOrderId(entity.getOrderId());
		ordersInput.setStatus(entity.getStatus());
		
		ordersInput.setCustomerId(Integer.parseInt(customers.getCustomerId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ordersInput);
	
		mvc.perform(put("/orders/" + entity.getOrderId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Orders de = createUpdateEntity();
		de.setOrderId(entity.getOrderId());
		orders_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/orders?search=orderId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetCustomers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orders/999/customers")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetCustomers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orders/" + orders.getOrderId()+ "/customers")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	
	@Test
	public void GetOrderitems_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("orderId", "1");
		
        Mockito.when(ordersAppService.parseOrderitemsJoinColumn("orderId")).thenReturn(joinCol);
		mvc.perform(get("/orders/1/orderitems?search=orderId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetOrderitems_searchIsNotEmpty() {
	
		Mockito.when(ordersAppService.parseOrderitemsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orders/1/orderitems?search=orderId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}
