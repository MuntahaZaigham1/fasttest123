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
import com.fastcode.example.application.orderitems.OrderitemsAppService;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.domain.orderitems.IOrderitemsRepository;
import com.fastcode.example.domain.orderitems.Orderitems;

import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.customers.ICustomersRepository;
import com.fastcode.example.domain.customers.Customers;
import com.fastcode.example.application.orders.OrdersAppService;
import com.fastcode.example.application.products.ProductsAppService;
import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.TestConstants;
import com.fastcode.example.domain.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class OrderitemsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("orderitemsRepository") 
	protected IOrderitemsRepository orderitems_repository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("customersRepository") 
	protected ICustomersRepository customersRepository;
	

	@SpyBean
	@Qualifier("orderitemsAppService")
	protected OrderitemsAppService orderitemsAppService;
	
    @SpyBean
    @Qualifier("ordersAppService")
	protected OrdersAppService  ordersAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Orderitems orderitems;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countProducts = 10;
	
	int countCustomers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.orderitems CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.orders CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.customers CASCADE").executeUpdate();
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
	public Products createProductsEntity() {
	
		if(countProducts>60) {
			countProducts = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Products productsEntity = new Products();
		
  		productsEntity.setName(String.valueOf(relationCount));
		productsEntity.setPrice(bigdec);
		bigdec = bigdec.add(BigDecimal.valueOf(1.2D));
		productsEntity.setProductId(relationCount);
		productsEntity.setStock(relationCount);
		productsEntity.setVersiono(0L);
		relationCount++;
		if(!productsRepository.findAll().contains(productsEntity))
		{
			 productsEntity = productsRepository.save(productsEntity);
		}
		countProducts++;
	    return productsEntity;
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

	public Orderitems createEntity() {
		Orders orders = createOrdersEntity();
		Products products = createProductsEntity();
	
		Orderitems orderitemsEntity = new Orderitems();
		orderitemsEntity.setOrderItemId(1);
    	orderitemsEntity.setPriceAtOrderTime(new BigDecimal("1.1"));
		orderitemsEntity.setQuantity(1);
		orderitemsEntity.setVersiono(0L);
		orderitemsEntity.setOrders(orders);
		orderitemsEntity.setProducts(products);
		
		return orderitemsEntity;
	}
    public CreateOrderitemsInput createOrderitemsInput() {
	
	    CreateOrderitemsInput orderitemsInput = new CreateOrderitemsInput();
    	orderitemsInput.setPriceAtOrderTime(new BigDecimal("5.8"));
		orderitemsInput.setQuantity(5);
		
		return orderitemsInput;
	}

	public Orderitems createNewEntity() {
		Orderitems orderitems = new Orderitems();
		orderitems.setOrderItemId(3);
    	orderitems.setPriceAtOrderTime(new BigDecimal("3.3"));
		orderitems.setQuantity(3);
		
		return orderitems;
	}
	
	public Orderitems createUpdateEntity() {
		Orderitems orderitems = new Orderitems();
		orderitems.setOrderItemId(4);
    	orderitems.setPriceAtOrderTime(new BigDecimal("3.3"));
		orderitems.setQuantity(4);
		
		return orderitems;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final OrderitemsController orderitemsController = new OrderitemsController(orderitemsAppService, ordersAppService, productsAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(orderitemsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		orderitems= createEntity();
		List<Orderitems> list= orderitems_repository.findAll();
		if(!list.contains(orderitems)) {
			orderitems=orderitems_repository.save(orderitems);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/orderitems/" + orderitems.getOrderItemId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orderitems/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateOrderitems_OrderitemsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateOrderitemsInput orderitemsInput = createOrderitemsInput();	
		
	    
		Orders orders =  createOrdersEntity();

		orderitemsInput.setOrderId(Integer.parseInt(orders.getOrderId().toString()));
	    
		Products products =  createProductsEntity();

		orderitemsInput.setProductId(Integer.parseInt(products.getProductId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(orderitemsInput);

		mvc.perform(post("/orderitems").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteOrderitems_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(orderitemsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/orderitems/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a orderitems with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Orderitems entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderitems_repository.save(entity);
		

		FindOrderitemsByIdOutput output= new FindOrderitemsByIdOutput();
		output.setOrderItemId(entity.getOrderItemId());
		output.setPriceAtOrderTime(entity.getPriceAtOrderTime());
		output.setQuantity(entity.getQuantity());
		
         Mockito.doReturn(output).when(orderitemsAppService).findById(entity.getOrderItemId());
       
    //    Mockito.when(orderitemsAppService.findById(entity.getOrderItemId())).thenReturn(output);
        
		mvc.perform(delete("/orderitems/" + entity.getOrderItemId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateOrderitems_OrderitemsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(orderitemsAppService).findById(999);
        
        UpdateOrderitemsInput orderitems = new UpdateOrderitemsInput();
		orderitems.setOrderItemId(999);
		orderitems.setPriceAtOrderTime(new BigDecimal("999"));
		orderitems.setQuantity(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderitems);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/orderitems/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Orderitems with id=999 not found."));
	}    

	@Test
	public void UpdateOrderitems_OrderitemsExists_ReturnStatusOk() throws Exception {
		Orderitems entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderitems_repository.save(entity);
		FindOrderitemsByIdOutput output= new FindOrderitemsByIdOutput();
		output.setOrderItemId(entity.getOrderItemId());
		output.setPriceAtOrderTime(entity.getPriceAtOrderTime());
		output.setQuantity(entity.getQuantity());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(orderitemsAppService.findById(entity.getOrderItemId())).thenReturn(output);
        
        
		
		UpdateOrderitemsInput orderitemsInput = new UpdateOrderitemsInput();
		orderitemsInput.setOrderItemId(entity.getOrderItemId());
		orderitemsInput.setPriceAtOrderTime(entity.getPriceAtOrderTime());
		orderitemsInput.setQuantity(entity.getQuantity());
		
		orderitemsInput.setOrderId(Integer.parseInt(orders.getOrderId().toString()));
		orderitemsInput.setProductId(Integer.parseInt(products.getProductId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderitemsInput);
	
		mvc.perform(put("/orderitems/" + entity.getOrderItemId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Orderitems de = createUpdateEntity();
		de.setOrderItemId(entity.getOrderItemId());
		orderitems_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/orderitems?search=orderItemId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetOrders_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderitems/999/orders")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetOrders_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderitems/" + orderitems.getOrderItemId()+ "/orders")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderitems/999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderitems/" + orderitems.getOrderItemId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}
