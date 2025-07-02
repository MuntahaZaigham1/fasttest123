package com.fastcode.example.application.suppliers;

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

import com.fastcode.example.domain.suppliers.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.suppliers.dto.*;
import com.fastcode.example.domain.suppliers.QSuppliers;
import com.fastcode.example.domain.suppliers.Suppliers;

import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import com.fastcode.example.domain.productsuppliers.IProductsuppliersRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class SuppliersAppServiceTest {

	@InjectMocks
	@Spy
	protected SuppliersAppService _appService;
	@Mock
	protected ISuppliersRepository _suppliersRepository;
	
	@Mock
	protected ISuppliersMapper _mapper;

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
	public void findSuppliersById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Suppliers> nullOptional = Optional.ofNullable(null);
		Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findSuppliersById_IdIsNotNullAndIdExists_ReturnSuppliers() {

		Suppliers suppliers = mock(Suppliers.class);
		Optional<Suppliers> suppliersOptional = Optional.of((Suppliers) suppliers);
		Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(suppliersOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.suppliersToFindSuppliersByIdOutput(suppliers));
	}
	
	
	@Test 
    public void createSuppliers_SuppliersIsNotNullAndSuppliersDoesNotExist_StoreSuppliers() { 
 
        Suppliers suppliersEntity = mock(Suppliers.class); 
    	CreateSuppliersInput suppliersInput = new CreateSuppliersInput();
		
        Mockito.when(_mapper.createSuppliersInputToSuppliers(any(CreateSuppliersInput.class))).thenReturn(suppliersEntity); 
        Mockito.when(_suppliersRepository.save(any(Suppliers.class))).thenReturn(suppliersEntity);

	   	Assertions.assertThat(_appService.create(suppliersInput)).isEqualTo(_mapper.suppliersToCreateSuppliersOutput(suppliersEntity));

    } 
	@Test
	public void updateSuppliers_SuppliersIdIsNotNullAndIdExists_ReturnUpdatedSuppliers() {

		Suppliers suppliersEntity = mock(Suppliers.class);
		UpdateSuppliersInput suppliers= mock(UpdateSuppliersInput.class);
		
		Optional<Suppliers> suppliersOptional = Optional.of((Suppliers) suppliersEntity);
		Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(suppliersOptional);
	 		
		Mockito.when(_mapper.updateSuppliersInputToSuppliers(any(UpdateSuppliersInput.class))).thenReturn(suppliersEntity);
		Mockito.when(_suppliersRepository.save(any(Suppliers.class))).thenReturn(suppliersEntity);
		Assertions.assertThat(_appService.update(ID,suppliers)).isEqualTo(_mapper.suppliersToUpdateSuppliersOutput(suppliersEntity));
	}
    
	@Test
	public void deleteSuppliers_SuppliersIsNotNullAndSuppliersExists_SuppliersRemoved() {

		Suppliers suppliers = mock(Suppliers.class);
		Optional<Suppliers> suppliersOptional = Optional.of((Suppliers) suppliers);
		Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(suppliersOptional);
 		
		_appService.delete(ID); 
		verify(_suppliersRepository).delete(suppliers);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Suppliers> list = new ArrayList<>();
		Page<Suppliers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindSuppliersByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_suppliersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Suppliers> list = new ArrayList<>();
		Suppliers suppliers = mock(Suppliers.class);
		list.add(suppliers);
    	Page<Suppliers> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindSuppliersByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.suppliersToFindSuppliersByIdOutput(suppliers));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_suppliersRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QSuppliers suppliers = QSuppliers.suppliersEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("contactEmail",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(suppliers.contactEmail.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(suppliers,map,searchMap)).isEqualTo(builder);
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
        list.add("contactEmail");
        list.add("name");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QSuppliers suppliers = QSuppliers.suppliersEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("contactEmail");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(suppliers.contactEmail.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QSuppliers.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

	@Test
	public void ParseproductsuppliersJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("supplierId", keyString);
		Assertions.assertThat(_appService.parseProductsuppliersJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}