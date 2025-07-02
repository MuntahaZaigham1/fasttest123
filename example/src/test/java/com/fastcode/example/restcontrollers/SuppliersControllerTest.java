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
import com.fastcode.example.application.suppliers.SuppliersAppService;
import com.fastcode.example.application.suppliers.dto.*;
import com.fastcode.example.domain.suppliers.ISuppliersRepository;
import com.fastcode.example.domain.suppliers.Suppliers;

import com.fastcode.example.application.productsuppliers.ProductsuppliersAppService;
import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.TestConstants;
import com.fastcode.example.domain.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class SuppliersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("suppliersRepository") 
	protected ISuppliersRepository suppliers_repository;
	

	@SpyBean
	@Qualifier("suppliersAppService")
	protected SuppliersAppService suppliersAppService;
	
    @SpyBean
    @Qualifier("productsuppliersAppService")
	protected ProductsuppliersAppService  productsuppliersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Suppliers suppliers;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.suppliers CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public Suppliers createEntity() {
	
		Suppliers suppliersEntity = new Suppliers();
		suppliersEntity.setContactEmail("1");
		suppliersEntity.setName("1");
		suppliersEntity.setSupplierId(1);
		suppliersEntity.setVersiono(0L);
		
		return suppliersEntity;
	}
    public CreateSuppliersInput createSuppliersInput() {
	
	    CreateSuppliersInput suppliersInput = new CreateSuppliersInput();
  		suppliersInput.setContactEmail("5");
  		suppliersInput.setName("5");
		
		return suppliersInput;
	}

	public Suppliers createNewEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setContactEmail("3");
		suppliers.setName("3");
		suppliers.setSupplierId(3);
		
		return suppliers;
	}
	
	public Suppliers createUpdateEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setContactEmail("4");
		suppliers.setName("4");
		suppliers.setSupplierId(4);
		
		return suppliers;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final SuppliersController suppliersController = new SuppliersController(suppliersAppService, productsuppliersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(suppliersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		suppliers= createEntity();
		List<Suppliers> list= suppliers_repository.findAll();
		if(!list.contains(suppliers)) {
			suppliers=suppliers_repository.save(suppliers);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/suppliers/" + suppliers.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateSuppliers_SuppliersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateSuppliersInput suppliersInput = createSuppliersInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(suppliersInput);

		mvc.perform(post("/suppliers").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteSuppliers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(suppliersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a suppliers with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Suppliers entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = suppliers_repository.save(entity);
		

		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setName(entity.getName());
		output.setSupplierId(entity.getSupplierId());
		
         Mockito.doReturn(output).when(suppliersAppService).findById(entity.getSupplierId());
       
    //    Mockito.when(suppliersAppService.findById(entity.getSupplierId())).thenReturn(output);
        
		mvc.perform(delete("/suppliers/" + entity.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateSuppliers_SuppliersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(suppliersAppService).findById(999);
        
        UpdateSuppliersInput suppliers = new UpdateSuppliersInput();
  		suppliers.setContactEmail("999");
  		suppliers.setName("999");
		suppliers.setSupplierId(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliers);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/suppliers/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Suppliers with id=999 not found."));
	}    

	@Test
	public void UpdateSuppliers_SuppliersExists_ReturnStatusOk() throws Exception {
		Suppliers entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = suppliers_repository.save(entity);
		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setContactEmail(entity.getContactEmail());
		output.setName(entity.getName());
		output.setSupplierId(entity.getSupplierId());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(suppliersAppService.findById(entity.getSupplierId())).thenReturn(output);
        
        
		
		UpdateSuppliersInput suppliersInput = new UpdateSuppliersInput();
		suppliersInput.setName(entity.getName());
		suppliersInput.setSupplierId(entity.getSupplierId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliersInput);
	
		mvc.perform(put("/suppliers/" + entity.getSupplierId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Suppliers de = createUpdateEntity();
		de.setSupplierId(entity.getSupplierId());
		suppliers_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/suppliers?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetProductsuppliers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("supplierId", "1");
		
        Mockito.when(suppliersAppService.parseProductsuppliersJoinColumn("supplierId")).thenReturn(joinCol);
		mvc.perform(get("/suppliers/1/productsuppliers?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetProductsuppliers_searchIsNotEmpty() {
	
		Mockito.when(suppliersAppService.parseProductsuppliersJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/suppliers/1/productsuppliers?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}
