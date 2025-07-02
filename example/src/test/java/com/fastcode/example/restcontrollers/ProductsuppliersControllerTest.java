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
import com.fastcode.example.application.productsuppliers.ProductsuppliersAppService;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.domain.productsuppliers.IProductsuppliersRepository;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;

import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.suppliers.ISuppliersRepository;
import com.fastcode.example.domain.suppliers.Suppliers;
import com.fastcode.example.application.products.ProductsAppService;
import com.fastcode.example.application.suppliers.SuppliersAppService;
import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.TestConstants;
import com.fastcode.example.domain.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class ProductsuppliersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("productsuppliersRepository") 
	protected IProductsuppliersRepository productsuppliers_repository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("suppliersRepository") 
	protected ISuppliersRepository suppliersRepository;
	

	@SpyBean
	@Qualifier("productsuppliersAppService")
	protected ProductsuppliersAppService productsuppliersAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
    @SpyBean
    @Qualifier("suppliersAppService")
	protected SuppliersAppService  suppliersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Productsuppliers productsuppliers;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countProducts = 10;
	
	int countSuppliers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.productsuppliers CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.suppliers CASCADE").executeUpdate();
		em.getTransaction().commit();
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
	public Suppliers createSuppliersEntity() {
	
		if(countSuppliers>60) {
			countSuppliers = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Suppliers suppliersEntity = new Suppliers();
		
  		suppliersEntity.setContactEmail(String.valueOf(relationCount));
  		suppliersEntity.setName(String.valueOf(relationCount));
		suppliersEntity.setSupplierId(relationCount);
		suppliersEntity.setVersiono(0L);
		relationCount++;
		if(!suppliersRepository.findAll().contains(suppliersEntity))
		{
			 suppliersEntity = suppliersRepository.save(suppliersEntity);
		}
		countSuppliers++;
	    return suppliersEntity;
	}

	public Productsuppliers createEntity() {
		Products products = createProductsEntity();
		Suppliers suppliers = createSuppliersEntity();
	
		Productsuppliers productsuppliersEntity = new Productsuppliers();
		productsuppliersEntity.setProductId(1);
		productsuppliersEntity.setSupplierId(1);
		productsuppliersEntity.setVersiono(0L);
		productsuppliersEntity.setProducts(products);
		productsuppliersEntity.setProductId(Integer.parseInt(products.getProductId().toString()));
		productsuppliersEntity.setSuppliers(suppliers);
		productsuppliersEntity.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));
		
		return productsuppliersEntity;
	}
    public CreateProductsuppliersInput createProductsuppliersInput() {
	
	    CreateProductsuppliersInput productsuppliersInput = new CreateProductsuppliersInput();
		productsuppliersInput.setProductId(5);
		productsuppliersInput.setSupplierId(5);
		
		return productsuppliersInput;
	}

	public Productsuppliers createNewEntity() {
		Productsuppliers productsuppliers = new Productsuppliers();
		productsuppliers.setProductId(3);
		productsuppliers.setSupplierId(3);
		
		return productsuppliers;
	}
	
	public Productsuppliers createUpdateEntity() {
		Productsuppliers productsuppliers = new Productsuppliers();
		productsuppliers.setProductId(4);
		productsuppliers.setSupplierId(4);
		
		return productsuppliers;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final ProductsuppliersController productsuppliersController = new ProductsuppliersController(productsuppliersAppService, productsAppService, suppliersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(productsuppliersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		productsuppliers= createEntity();
		List<Productsuppliers> list= productsuppliers_repository.findAll();
		if(!list.contains(productsuppliers)) {
			productsuppliers=productsuppliers_repository.save(productsuppliers);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/productsuppliers/productId=" + productsuppliers.getProductId()+ ",supplierId=" + productsuppliers.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/productsuppliers/productId=999,supplierId=999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateProductsuppliers_ProductsuppliersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateProductsuppliersInput productsuppliersInput = createProductsuppliersInput();	
		
	    
		Products products =  createProductsEntity();

		productsuppliersInput.setProductId(Integer.parseInt(products.getProductId().toString()));
	    
		Suppliers suppliers =  createSuppliersEntity();

		productsuppliersInput.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(productsuppliersInput);

		mvc.perform(post("/productsuppliers").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteProductsuppliers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(productsuppliersAppService).findById(new ProductsuppliersId(999, 999));
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/productsuppliers/productId=999,supplierId=999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a productsuppliers with a id=productId=999,supplierId=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Productsuppliers entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Products products = createProductsEntity();
		entity.setProductId(Integer.parseInt(products.getProductId().toString()));
		entity.setProducts(products);
		Suppliers suppliers = createSuppliersEntity();
		entity.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));
		entity.setSuppliers(suppliers);
		entity = productsuppliers_repository.save(entity);
		

		FindProductsuppliersByIdOutput output= new FindProductsuppliersByIdOutput();
		output.setProductId(entity.getProductId());
		output.setSupplierId(entity.getSupplierId());
		
	//    Mockito.when(productsuppliersAppService.findById(new ProductsuppliersId(entity.getProductId(), entity.getSupplierId()))).thenReturn(output);
        Mockito.doReturn(output).when(productsuppliersAppService).findById(new ProductsuppliersId(entity.getProductId(), entity.getSupplierId()));
        
		mvc.perform(delete("/productsuppliers/productId="+ entity.getProductId()+ ",supplierId="+ entity.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateProductsuppliers_ProductsuppliersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(productsuppliersAppService).findById(new ProductsuppliersId(999, 999));
        
        UpdateProductsuppliersInput productsuppliers = new UpdateProductsuppliersInput();
		productsuppliers.setProductId(999);
		productsuppliers.setSupplierId(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(productsuppliers);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/productsuppliers/productId=999,supplierId=999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Productsuppliers with id=productId=999,supplierId=999 not found."));
	}    

	@Test
	public void UpdateProductsuppliers_ProductsuppliersExists_ReturnStatusOk() throws Exception {
		Productsuppliers entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Products products = createProductsEntity();
		entity.setProductId(Integer.parseInt(products.getProductId().toString()));
		entity.setProducts(products);
		Suppliers suppliers = createSuppliersEntity();
		entity.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));
		entity.setSuppliers(suppliers);
		entity = productsuppliers_repository.save(entity);
		FindProductsuppliersByIdOutput output= new FindProductsuppliersByIdOutput();
		output.setProductId(entity.getProductId());
		output.setSupplierId(entity.getSupplierId());
		output.setVersiono(entity.getVersiono());
		
	    Mockito.when(productsuppliersAppService.findById(new ProductsuppliersId(entity.getProductId(), entity.getSupplierId()))).thenReturn(output);
        
        
		
		UpdateProductsuppliersInput productsuppliersInput = new UpdateProductsuppliersInput();
		productsuppliersInput.setProductId(entity.getProductId());
		productsuppliersInput.setSupplierId(entity.getSupplierId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(productsuppliersInput);
	
		mvc.perform(put("/productsuppliers/productId=" + entity.getProductId()+ ",supplierId=" + entity.getSupplierId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Productsuppliers de = createUpdateEntity();
		de.setProductId(entity.getProductId());
		de.setSupplierId(entity.getSupplierId());
		productsuppliers_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/productsuppliers?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
		
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/productsuppliers/productId999/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid id=productId999"));
	
	}    
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/productsuppliers/productId=999,supplierId=999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/productsuppliers/productId=" + productsuppliers.getProductId()+ ",supplierId=" + productsuppliers.getSupplierId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetSuppliers_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
		
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/productsuppliers/productId999/suppliers")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid id=productId999"));
	
	}    
	@Test
	public void GetSuppliers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/productsuppliers/productId=999,supplierId=999/suppliers")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetSuppliers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/productsuppliers/productId=" + productsuppliers.getProductId()+ ",supplierId=" + productsuppliers.getSupplierId()+ "/suppliers")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}
