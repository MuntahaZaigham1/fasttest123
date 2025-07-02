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
import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.application.productsuppliers.IProductsuppliersAppService;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.application.products.IProductsAppService;
import com.fastcode.example.application.suppliers.ISuppliersAppService;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.net.MalformedURLException;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/productsuppliers")
@RequiredArgsConstructor
public class ProductsuppliersController {

	@Qualifier("productsuppliersAppService")
	@NonNull protected final IProductsuppliersAppService _productsuppliersAppService;
    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

    @Qualifier("suppliersAppService")
	@NonNull  protected final ISuppliersAppService  _suppliersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateProductsuppliersOutput> create( @RequestBody @Valid CreateProductsuppliersInput productsuppliers) {
		CreateProductsuppliersOutput output=_productsuppliersAppService.create(productsuppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete productsuppliers ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

		ProductsuppliersId productsuppliersid =_productsuppliersAppService.parseProductsuppliersKey(id);
		if(productsuppliersid == null) {
			throw new EntityNotFoundException(String.format("Invalid id=%s", id));
		}	

		FindProductsuppliersByIdOutput output = _productsuppliersAppService.findById(productsuppliersid);
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a productsuppliers with a id=%s", id));
    	}	

		_productsuppliersAppService.delete(productsuppliersid);
    }


	// ------------ Update productsuppliers ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateProductsuppliersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateProductsuppliersInput productsuppliers) {

		ProductsuppliersId productsuppliersid =_productsuppliersAppService.parseProductsuppliersKey(id);

		if(productsuppliersid == null) {
			throw new EntityNotFoundException(String.format("Invalid id=%s", id));
		}

		FindProductsuppliersByIdOutput currentProductsuppliers = _productsuppliersAppService.findById(productsuppliersid);
		if(currentProductsuppliers == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Productsuppliers with id=%s not found.", id));
		}

		productsuppliers.setVersiono(currentProductsuppliers.getVersiono());
		UpdateProductsuppliersOutput output = _productsuppliersAppService.update(productsuppliersid,productsuppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindProductsuppliersByIdOutput> findById(@PathVariable String id) {

		ProductsuppliersId productsuppliersid =_productsuppliersAppService.parseProductsuppliersKey(id);
		if(productsuppliersid == null) {
			throw new EntityNotFoundException(String.format("Invalid id=%s", id));
		}	

		FindProductsuppliersByIdOutput output = _productsuppliersAppService.findById(productsuppliersid);
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsuppliersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_productsuppliersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProductsOutput> getProducts(@PathVariable String id) {
		ProductsuppliersId productsuppliersid =_productsuppliersAppService.parseProductsuppliersKey(id);
		if(productsuppliersid == null) {
			throw new EntityNotFoundException(String.format("Invalid id=%s",id));
		}

		GetProductsOutput output= _productsuppliersAppService.getProducts(productsuppliersid);
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}/suppliers", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetSuppliersOutput> getSuppliers(@PathVariable String id) {
		ProductsuppliersId productsuppliersid =_productsuppliersAppService.parseProductsuppliersKey(id);
		if(productsuppliersid == null) {
			throw new EntityNotFoundException(String.format("Invalid id=%s",id));
		}

		GetSuppliersOutput output= _productsuppliersAppService.getSuppliers(productsuppliersid);
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}

