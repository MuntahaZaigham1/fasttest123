package com.fastcode.example.application.productsuppliers;

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

import com.fastcode.example.domain.productsuppliers.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.domain.productsuppliers.QProductsuppliers;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;

import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.domain.suppliers.Suppliers;
import com.fastcode.example.domain.suppliers.ISuppliersRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductsuppliersAppServiceTest {

	@InjectMocks
	@Spy
	protected ProductsuppliersAppService _appService;
	@Mock
	protected IProductsuppliersRepository _productsuppliersRepository;
	
    @Mock
	protected IProductsRepository _productsRepository;

    @Mock
	protected ISuppliersRepository _suppliersRepository;

	@Mock
	protected IProductsuppliersMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    @Mock
    protected ProductsuppliersId productsuppliersId;
    
    private static final Long ID = 15L;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findProductsuppliersById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Productsuppliers> nullOptional = Optional.ofNullable(null);
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(productsuppliersId)).isEqualTo(null);
	}
	
	@Test
	public void findProductsuppliersById_IdIsNotNullAndIdExists_ReturnProductsuppliers() {

		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliers);
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);
		
	    Assertions.assertThat(_appService.findById(productsuppliersId)).isEqualTo(_mapper.productsuppliersToFindProductsuppliersByIdOutput(productsuppliers));
	}
	
	
	@Test 
    public void createProductsuppliers_ProductsuppliersIsNotNullAndProductsuppliersDoesNotExist_StoreProductsuppliers() { 
 
        Productsuppliers productsuppliersEntity = mock(Productsuppliers.class); 
    	CreateProductsuppliersInput productsuppliersInput = new CreateProductsuppliersInput();
		
        Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
        productsuppliersInput.setProductId(15);
		
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
        
		
        Suppliers suppliers = mock(Suppliers.class);
		Optional<Suppliers> suppliersOptional = Optional.of((Suppliers) suppliers);
        productsuppliersInput.setSupplierId(15);
		
        Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(suppliersOptional);
        
		
        Mockito.when(_mapper.createProductsuppliersInputToProductsuppliers(any(CreateProductsuppliersInput.class))).thenReturn(productsuppliersEntity); 
        Mockito.when(_productsuppliersRepository.save(any(Productsuppliers.class))).thenReturn(productsuppliersEntity);

	   	Assertions.assertThat(_appService.create(productsuppliersInput)).isEqualTo(_mapper.productsuppliersToCreateProductsuppliersOutput(productsuppliersEntity));

    } 
    @Test
	public void createProductsuppliers_ProductsuppliersIsNotNullAndProductsuppliersDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreProductsuppliers() {

		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		CreateProductsuppliersInput productsuppliersInput = mock(CreateProductsuppliersInput.class);
		
		
		Mockito.when(_mapper.createProductsuppliersInputToProductsuppliers(any(CreateProductsuppliersInput.class))).thenReturn(productsuppliers);
		Mockito.when(_productsuppliersRepository.save(any(Productsuppliers.class))).thenReturn(productsuppliers);
	    Assertions.assertThat(_appService.create(productsuppliersInput)).isEqualTo(_mapper.productsuppliersToCreateProductsuppliersOutput(productsuppliers)); 
	}
	
    @Test
	public void updateProductsuppliers_ProductsuppliersIsNotNullAndProductsuppliersDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedProductsuppliers() {

		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		UpdateProductsuppliersInput productsuppliersInput = mock(UpdateProductsuppliersInput.class);
		
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliers);
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);
		
		Mockito.when(_mapper.updateProductsuppliersInputToProductsuppliers(any(UpdateProductsuppliersInput.class))).thenReturn(productsuppliers);
		Mockito.when(_productsuppliersRepository.save(any(Productsuppliers.class))).thenReturn(productsuppliers);
		Assertions.assertThat(_appService.update(productsuppliersId,productsuppliersInput)).isEqualTo(_mapper.productsuppliersToUpdateProductsuppliersOutput(productsuppliers));
	}
	
		
	@Test
	public void updateProductsuppliers_ProductsuppliersIdIsNotNullAndIdExists_ReturnUpdatedProductsuppliers() {

		Productsuppliers productsuppliersEntity = mock(Productsuppliers.class);
		UpdateProductsuppliersInput productsuppliers= mock(UpdateProductsuppliersInput.class);
		
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliersEntity);
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);
	 		
		Mockito.when(_mapper.updateProductsuppliersInputToProductsuppliers(any(UpdateProductsuppliersInput.class))).thenReturn(productsuppliersEntity);
		Mockito.when(_productsuppliersRepository.save(any(Productsuppliers.class))).thenReturn(productsuppliersEntity);
		Assertions.assertThat(_appService.update(productsuppliersId,productsuppliers)).isEqualTo(_mapper.productsuppliersToUpdateProductsuppliersOutput(productsuppliersEntity));
	}
    
	@Test
	public void deleteProductsuppliers_ProductsuppliersIsNotNullAndProductsuppliersExists_ProductsuppliersRemoved() {

		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliers);
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);
 		
		_appService.delete(productsuppliersId); 
		verify(_productsuppliersRepository).delete(productsuppliers);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Productsuppliers> list = new ArrayList<>();
		Page<Productsuppliers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProductsuppliersByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_productsuppliersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Productsuppliers> list = new ArrayList<>();
		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		list.add(productsuppliers);
    	Page<Productsuppliers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProductsuppliersByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.productsuppliersToFindProductsuppliersByIdOutput(productsuppliers));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_productsuppliersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QProductsuppliers productsuppliers = QProductsuppliers.productsuppliersEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(productsuppliers,map,searchMap)).isEqualTo(builder);
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
		QProductsuppliers productsuppliers = QProductsuppliers.productsuppliersEntity;
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
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QProductsuppliers.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Products
	@Test
	public void GetProducts_IfProductsuppliersIdAndProductsIdIsNotNullAndProductsuppliersExists_ReturnProducts() {
		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliers);
		Products productsEntity = mock(Products.class);

		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);

		Mockito.when(productsuppliers.getProducts()).thenReturn(productsEntity);
		Assertions.assertThat(_appService.getProducts(productsuppliersId)).isEqualTo(_mapper.productsToGetProductsOutput(productsEntity, productsuppliers));
	}

	@Test 
	public void GetProducts_IfProductsuppliersIdAndProductsIdIsNotNullAndProductsuppliersDoesNotExist_ReturnNull() {
		Optional<Productsuppliers> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProducts(productsuppliersId)).isEqualTo(null);
	}
   
    //Suppliers
	@Test
	public void GetSuppliers_IfProductsuppliersIdAndSuppliersIdIsNotNullAndProductsuppliersExists_ReturnSuppliers() {
		Productsuppliers productsuppliers = mock(Productsuppliers.class);
		Optional<Productsuppliers> productsuppliersOptional = Optional.of((Productsuppliers) productsuppliers);
		Suppliers suppliersEntity = mock(Suppliers.class);

		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(productsuppliersOptional);

		Mockito.when(productsuppliers.getSuppliers()).thenReturn(suppliersEntity);
		Assertions.assertThat(_appService.getSuppliers(productsuppliersId)).isEqualTo(_mapper.suppliersToGetSuppliersOutput(suppliersEntity, productsuppliers));
	}

	@Test 
	public void GetSuppliers_IfProductsuppliersIdAndSuppliersIdIsNotNullAndProductsuppliersDoesNotExist_ReturnNull() {
		Optional<Productsuppliers> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_productsuppliersRepository.findById(any(ProductsuppliersId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getSuppliers(productsuppliersId)).isEqualTo(null);
	}

  
	@Test
	public void ParseProductsuppliersKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnProductsuppliersId()
	{
		String keyString= "productId=15,supplierId=15";
	
		ProductsuppliersId productsuppliersId = new ProductsuppliersId();
        productsuppliersId.setProductId(15);
        productsuppliersId.setSupplierId(15);

		Assertions.assertThat(_appService.parseProductsuppliersKey(keyString)).isEqualToComparingFieldByField(productsuppliersId);
	}
	
	@Test
	public void ParseProductsuppliersKey_KeysStringIsEmpty_ReturnNull()
	{
		String keyString= "";
		Assertions.assertThat(_appService.parseProductsuppliersKey(keyString)).isEqualTo(null);
	}
	
	@Test
	public void ParseProductsuppliersKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
		String keyString= "productId";

		Assertions.assertThat(_appService.parseProductsuppliersKey(keyString)).isEqualTo(null);
	}
}