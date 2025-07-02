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
import com.fastcode.example.application.orderitems.IOrderitemsAppService;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.application.orders.IOrdersAppService;
import com.fastcode.example.application.products.IProductsAppService;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.net.MalformedURLException;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orderitems")
@RequiredArgsConstructor
public class OrderitemsController {

	@Qualifier("orderitemsAppService")
	@NonNull protected final IOrderitemsAppService _orderitemsAppService;
    @Qualifier("ordersAppService")
	@NonNull  protected final IOrdersAppService  _ordersAppService;

    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateOrderitemsOutput> create( @RequestBody @Valid CreateOrderitemsInput orderitems) {
		CreateOrderitemsOutput output=_orderitemsAppService.create(orderitems);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete orderitems ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindOrderitemsByIdOutput output = _orderitemsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a orderitems with a id=%s", id));
    	}	

    	_orderitemsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update orderitems ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateOrderitemsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateOrderitemsInput orderitems) {

	    FindOrderitemsByIdOutput currentOrderitems = _orderitemsAppService.findById(Integer.valueOf(id));
		if(currentOrderitems == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Orderitems with id=%s not found.", id));
		}

		orderitems.setVersiono(currentOrderitems.getVersiono());
	    UpdateOrderitemsOutput output = _orderitemsAppService.update(Integer.valueOf(id),orderitems);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindOrderitemsByIdOutput> findById(@PathVariable String id) {

    	FindOrderitemsByIdOutput output = _orderitemsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrderitemsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_orderitemsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/orders", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetOrdersOutput> getOrders(@PathVariable String id) {
    	GetOrdersOutput output= _orderitemsAppService.getOrders(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProductsOutput> getProducts(@PathVariable String id) {
    	GetProductsOutput output= _orderitemsAppService.getProducts(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}

