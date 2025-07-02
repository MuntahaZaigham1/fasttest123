package com.fastcode.example.restcontrollers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.lang.reflect.InvocationTargetException;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.application.products.IProductsAppService;
import com.fastcode.example.application.products.dto.*;
import com.fastcode.example.application.orderitems.IOrderitemsAppService;
import com.fastcode.example.application.orderitems.dto.FindOrderitemsByIdOutput;
import com.fastcode.example.application.productsuppliers.IProductsuppliersAppService;
import com.fastcode.example.application.productsuppliers.dto.FindProductsuppliersByIdOutput;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.net.MalformedURLException;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

	@Qualifier("productsAppService")
	@NonNull protected final IProductsAppService _productsAppService;
    @Qualifier("orderitemsAppService")
	@NonNull  protected final IOrderitemsAppService  _orderitemsAppService;

    @Qualifier("productsuppliersAppService")
	@NonNull  protected final IProductsuppliersAppService  _productsuppliersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateProductsOutput> create( @RequestBody @Valid CreateProductsInput products) {
		CreateProductsOutput output=_productsAppService.create(products);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete products ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindProductsByIdOutput output = _productsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a products with a id=%s", id));
    	}	

    	_productsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update products ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateProductsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateProductsInput products) {

	    FindProductsByIdOutput currentProducts = _productsAppService.findById(Integer.valueOf(id));
		if(currentProducts == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Products with id=%s not found.", id));
		}

		products.setVersiono(currentProducts.getVersiono());
	    UpdateProductsOutput output = _productsAppService.update(Integer.valueOf(id),products);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindProductsByIdOutput> findById(@PathVariable String id) {

    	FindProductsByIdOutput output = _productsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_productsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/orderitems", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrderitemsByIdOutput>> getOrderitems(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_productsAppService.parseOrderitemsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindOrderitemsByIdOutput> output = _orderitemsAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}/productsuppliers", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsuppliersByIdOutput>> getProductsuppliers(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_productsAppService.parseProductsuppliersJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindProductsuppliersByIdOutput> output = _productsuppliersAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}

