package com.fastcode.example.application.orderitems;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.domain.orderitems.IOrderitemsRepository;
import com.fastcode.example.domain.orderitems.QOrderitems;
import com.fastcode.example.domain.orderitems.Orderitems;
import com.fastcode.example.domain.orders.IOrdersRepository;
import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.domain.products.Products;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("orderitemsAppService")
@RequiredArgsConstructor
public class OrderitemsAppService implements IOrderitemsAppService {
    
	@Qualifier("orderitemsRepository")
	@NonNull protected final IOrderitemsRepository _orderitemsRepository;

	
    @Qualifier("ordersRepository")
	@NonNull protected final IOrdersRepository _ordersRepository;

    @Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

	@Qualifier("IOrderitemsMapperImpl")
	@NonNull protected final IOrderitemsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateOrderitemsOutput create(CreateOrderitemsInput input) {

		Orderitems orderitems = mapper.createOrderitemsInputToOrderitems(input);
		Orders foundOrders = null;
		Products foundProducts = null;
	  	if(input.getOrderId()!=null) {
			foundOrders = _ordersRepository.findById(input.getOrderId()).orElse(null);
			
			if(foundOrders!=null) {
				foundOrders.addOrderitems(orderitems);
			}
		}
	  	if(input.getProductId()!=null) {
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
			
			if(foundProducts!=null) {
				foundProducts.addOrderitems(orderitems);
			}
		}

		Orderitems createdOrderitems = _orderitemsRepository.save(orderitems);
		return mapper.orderitemsToCreateOrderitemsOutput(createdOrderitems);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateOrderitemsOutput update(Integer orderitemsId, UpdateOrderitemsInput input) {

		Orderitems existing = _orderitemsRepository.findById(orderitemsId).orElseThrow(() -> new EntityNotFoundException("Orderitems not found"));

		Orderitems orderitems = mapper.updateOrderitemsInputToOrderitems(input);
		Orders foundOrders = null;
		Products foundProducts = null;
        
	  	if(input.getOrderId()!=null) { 
			foundOrders = _ordersRepository.findById(input.getOrderId()).orElse(null);
		
			if(foundOrders!=null) {
				foundOrders.addOrderitems(orderitems);
			}
		}
        
	  	if(input.getProductId()!=null) { 
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
		
			if(foundProducts!=null) {
				foundProducts.addOrderitems(orderitems);
			}
		}
		
		Orderitems updatedOrderitems = _orderitemsRepository.save(orderitems);
		return mapper.orderitemsToUpdateOrderitemsOutput(updatedOrderitems);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer orderitemsId) {

		Orderitems existing = _orderitemsRepository.findById(orderitemsId).orElseThrow(() -> new EntityNotFoundException("Orderitems not found"));
	 	
        if(existing.getOrders() !=null)
        {
        existing.getOrders().removeOrderitems(existing);
        }
        if(existing.getProducts() !=null)
        {
        existing.getProducts().removeOrderitems(existing);
        }
        if(existing !=null) {
			_orderitemsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindOrderitemsByIdOutput findById(Integer orderitemsId) {

		Orderitems foundOrderitems = _orderitemsRepository.findById(orderitemsId).orElse(null);
		if (foundOrderitems == null)  
			return null; 
 	   
 	    return mapper.orderitemsToFindOrderitemsByIdOutput(foundOrderitems);
	}

    //Orders
	// ReST API Call - GET /orderitems/1/orders
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetOrdersOutput getOrders(Integer orderitemsId) {

		Orderitems foundOrderitems = _orderitemsRepository.findById(orderitemsId).orElse(null);
		if (foundOrderitems == null) {
			logHelper.getLogger().error("There does not exist a orderitems wth a id='{}'", orderitemsId);
			return null;
		}
		Orders re = foundOrderitems.getOrders();
		return mapper.ordersToGetOrdersOutput(re, foundOrderitems);
	}
	
    //Products
	// ReST API Call - GET /orderitems/1/products
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProductsOutput getProducts(Integer orderitemsId) {

		Orderitems foundOrderitems = _orderitemsRepository.findById(orderitemsId).orElse(null);
		if (foundOrderitems == null) {
			logHelper.getLogger().error("There does not exist a orderitems wth a id='{}'", orderitemsId);
			return null;
		}
		Products re = foundOrderitems.getProducts();
		return mapper.productsToGetProductsOutput(re, foundOrderitems);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindOrderitemsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Orderitems> foundOrderitems = _orderitemsRepository.findAll(search(search), pageable);
		List<Orderitems> orderitemsList = foundOrderitems.getContent();
		Iterator<Orderitems> orderitemsIterator = orderitemsList.iterator(); 
		List<FindOrderitemsByIdOutput> output = new ArrayList<>();

		while (orderitemsIterator.hasNext()) {
		Orderitems orderitems = orderitemsIterator.next();
 	    output.add(mapper.orderitemsToFindOrderitemsByIdOutput(orderitems));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QOrderitems orderitems= QOrderitems.orderitemsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(orderitems, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("orders") ||
				list.get(i).replace("%20","").trim().equals("orderId") ||
		        list.get(i).replace("%20","").trim().equals("products") ||
				list.get(i).replace("%20","").trim().equals("productId") ||
				list.get(i).replace("%20","").trim().equals("orderItemId") ||
				list.get(i).replace("%20","").trim().equals("priceAtOrderTime") ||
				list.get(i).replace("%20","").trim().equals("quantity")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QOrderitems orderitems, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("orderItemId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orderItemId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orderItemId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orderItemId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.orderItemId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderitems.orderItemId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.orderItemId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("priceAtOrderTime")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderitems.priceAtOrderTime.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderitems.priceAtOrderTime.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orderitems.priceAtOrderTime.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.priceAtOrderTime.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(orderitems.priceAtOrderTime.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.priceAtOrderTime.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
			if(details.getKey().replace("%20","").trim().equals("quantity")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.quantity.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.quantity.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.quantity.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.quantity.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderitems.quantity.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.quantity.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("orders")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orders.orderId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orders.orderId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.orders.orderId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.orders.orderId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderitems.orders.orderId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.orders.orderId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("products")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.products.productId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.products.productId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orderitems.products.productId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.products.productId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orderitems.products.productId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orderitems.products.productId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("orderId")) {
		    builder.and(orderitems.orders.orderId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("productId")) {
		    builder.and(orderitems.products.productId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		return builder;
	}
	
    
    
}


