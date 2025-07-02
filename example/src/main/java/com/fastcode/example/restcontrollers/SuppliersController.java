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
import com.fastcode.example.application.suppliers.ISuppliersAppService;
import com.fastcode.example.application.suppliers.dto.*;
import com.fastcode.example.application.productsuppliers.IProductsuppliersAppService;
import com.fastcode.example.application.productsuppliers.dto.FindProductsuppliersByIdOutput;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.net.MalformedURLException;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SuppliersController {

	@Qualifier("suppliersAppService")
	@NonNull protected final ISuppliersAppService _suppliersAppService;
    @Qualifier("productsuppliersAppService")
	@NonNull  protected final IProductsuppliersAppService  _productsuppliersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateSuppliersOutput> create( @RequestBody @Valid CreateSuppliersInput suppliers) {
		CreateSuppliersOutput output=_suppliersAppService.create(suppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete suppliers ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindSuppliersByIdOutput output = _suppliersAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a suppliers with a id=%s", id));
    	}	

    	_suppliersAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update suppliers ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateSuppliersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateSuppliersInput suppliers) {

	    FindSuppliersByIdOutput currentSuppliers = _suppliersAppService.findById(Integer.valueOf(id));
		if(currentSuppliers == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Suppliers with id=%s not found.", id));
		}

		suppliers.setVersiono(currentSuppliers.getVersiono());
	    UpdateSuppliersOutput output = _suppliersAppService.update(Integer.valueOf(id),suppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindSuppliersByIdOutput> findById(@PathVariable String id) {

    	FindSuppliersByIdOutput output = _suppliersAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindSuppliersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_suppliersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/productsuppliers", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsuppliersByIdOutput>> getProductsuppliers(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_suppliersAppService.parseProductsuppliersJoinColumn(id);
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

