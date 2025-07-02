package com.fastcode.example.application.productsuppliers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.productsuppliers.dto.*;
import com.fastcode.example.domain.productsuppliers.IProductsuppliersRepository;
import com.fastcode.example.domain.productsuppliers.QProductsuppliers;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import com.fastcode.example.domain.products.IProductsRepository;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.suppliers.ISuppliersRepository;
import com.fastcode.example.domain.suppliers.Suppliers;


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

@Service("productsuppliersAppService")
@RequiredArgsConstructor
public class ProductsuppliersAppService implements IProductsuppliersAppService {
    
	@Qualifier("productsuppliersRepository")
	@NonNull protected final IProductsuppliersRepository _productsuppliersRepository;

	
    @Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

    @Qualifier("suppliersRepository")
	@NonNull protected final ISuppliersRepository _suppliersRepository;

	@Qualifier("IProductsuppliersMapperImpl")
	@NonNull protected final IProductsuppliersMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateProductsuppliersOutput create(CreateProductsuppliersInput input) {

		Productsuppliers productsuppliers = mapper.createProductsuppliersInputToProductsuppliers(input);
		Products foundProducts = null;
		Suppliers foundSuppliers = null;
	  	if(input.getProductId()!=null) {
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
			
			if(foundProducts!=null) {
				foundProducts.addProductsuppliers(productsuppliers);
			}
		}
	  	if(input.getSupplierId()!=null) {
			foundSuppliers = _suppliersRepository.findById(input.getSupplierId()).orElse(null);
			
			if(foundSuppliers!=null) {
				foundSuppliers.addProductsuppliers(productsuppliers);
			}
		}

		Productsuppliers createdProductsuppliers = _productsuppliersRepository.save(productsuppliers);
		return mapper.productsuppliersToCreateProductsuppliersOutput(createdProductsuppliers);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateProductsuppliersOutput update(ProductsuppliersId productsuppliersId, UpdateProductsuppliersInput input) {

		Productsuppliers existing = _productsuppliersRepository.findById(productsuppliersId).orElseThrow(() -> new EntityNotFoundException("Productsuppliers not found"));

		Productsuppliers productsuppliers = mapper.updateProductsuppliersInputToProductsuppliers(input);
		Products foundProducts = null;
		Suppliers foundSuppliers = null;
        
	  	if(input.getProductId()!=null) { 
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
		
			if(foundProducts!=null) {
				foundProducts.addProductsuppliers(productsuppliers);
			}
		}
        
	  	if(input.getSupplierId()!=null) { 
			foundSuppliers = _suppliersRepository.findById(input.getSupplierId()).orElse(null);
		
			if(foundSuppliers!=null) {
				foundSuppliers.addProductsuppliers(productsuppliers);
			}
		}
		
		Productsuppliers updatedProductsuppliers = _productsuppliersRepository.save(productsuppliers);
		return mapper.productsuppliersToUpdateProductsuppliersOutput(updatedProductsuppliers);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ProductsuppliersId productsuppliersId) {

		Productsuppliers existing = _productsuppliersRepository.findById(productsuppliersId).orElseThrow(() -> new EntityNotFoundException("Productsuppliers not found"));
	 	
        if(existing.getProducts() !=null)
        {
        existing.getProducts().removeProductsuppliers(existing);
        }
        if(existing.getSuppliers() !=null)
        {
        existing.getSuppliers().removeProductsuppliers(existing);
        }
        if(existing !=null) {
			_productsuppliersRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindProductsuppliersByIdOutput findById(ProductsuppliersId productsuppliersId) {

		Productsuppliers foundProductsuppliers = _productsuppliersRepository.findById(productsuppliersId).orElse(null);
		if (foundProductsuppliers == null)  
			return null; 
 	   
 	    return mapper.productsuppliersToFindProductsuppliersByIdOutput(foundProductsuppliers);
	}

    //Products
	// ReST API Call - GET /productsuppliers/1/products
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProductsOutput getProducts(ProductsuppliersId productsuppliersId) {

		Productsuppliers foundProductsuppliers = _productsuppliersRepository.findById(productsuppliersId).orElse(null);
		if (foundProductsuppliers == null) {
			logHelper.getLogger().error("There does not exist a productsuppliers wth a id='{}'", productsuppliersId);
			return null;
		}
		Products re = foundProductsuppliers.getProducts();
		return mapper.productsToGetProductsOutput(re, foundProductsuppliers);
	}
	
    //Suppliers
	// ReST API Call - GET /productsuppliers/1/suppliers
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetSuppliersOutput getSuppliers(ProductsuppliersId productsuppliersId) {

		Productsuppliers foundProductsuppliers = _productsuppliersRepository.findById(productsuppliersId).orElse(null);
		if (foundProductsuppliers == null) {
			logHelper.getLogger().error("There does not exist a productsuppliers wth a id='{}'", productsuppliersId);
			return null;
		}
		Suppliers re = foundProductsuppliers.getSuppliers();
		return mapper.suppliersToGetSuppliersOutput(re, foundProductsuppliers);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindProductsuppliersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Productsuppliers> foundProductsuppliers = _productsuppliersRepository.findAll(search(search), pageable);
		List<Productsuppliers> productsuppliersList = foundProductsuppliers.getContent();
		Iterator<Productsuppliers> productsuppliersIterator = productsuppliersList.iterator(); 
		List<FindProductsuppliersByIdOutput> output = new ArrayList<>();

		while (productsuppliersIterator.hasNext()) {
		Productsuppliers productsuppliers = productsuppliersIterator.next();
 	    output.add(mapper.productsuppliersToFindProductsuppliersByIdOutput(productsuppliers));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QProductsuppliers productsuppliers= QProductsuppliers.productsuppliersEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(productsuppliers, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("products") ||
		        list.get(i).replace("%20","").trim().equals("suppliers") ||
				list.get(i).replace("%20","").trim().equals("productId") ||
				list.get(i).replace("%20","").trim().equals("supplierId")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QProductsuppliers productsuppliers, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("productId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.productId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.productId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.productId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.productId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(productsuppliers.productId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.productId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("supplierId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.supplierId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.supplierId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.supplierId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.supplierId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(productsuppliers.supplierId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.supplierId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("products")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.products.productId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.products.productId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.products.productId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.products.productId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(productsuppliers.products.productId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.products.productId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("suppliers")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.suppliers.supplierId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.suppliers.supplierId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(productsuppliers.suppliers.supplierId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.suppliers.supplierId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(productsuppliers.suppliers.supplierId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(productsuppliers.suppliers.supplierId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("productId")) {
		    builder.and(productsuppliers.products.productId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("supplierId")) {
		    builder.and(productsuppliers.suppliers.supplierId.eq(Integer.parseInt(joinCol.getValue())));
		}
        
        }
		return builder;
	}
	
	public ProductsuppliersId parseProductsuppliersKey(String keysString) {
		
		String[] keyEntries = keysString.split(",");
		ProductsuppliersId productsuppliersId = new ProductsuppliersId();
		
		Map<String,String> keyMap = new HashMap<String,String>();
		if(keyEntries.length > 1) {
			for(String keyEntry: keyEntries)
			{
				String[] keyEntryArr = keyEntry.split("=");
				if(keyEntryArr.length > 1) {
					keyMap.put(keyEntryArr[0], keyEntryArr[1]);					
				}
				else {
					return null;
				}
			}
		}
		else {
			String[] keyEntryArr = keysString.split("=");
			if(keyEntryArr.length > 1) {
				keyMap.put(keyEntryArr[0], keyEntryArr[1]);					
			}
			else {
				return null;
			}
		}
		
		productsuppliersId.setProductId(Integer.valueOf(keyMap.get("productId")));
		productsuppliersId.setSupplierId(Integer.valueOf(keyMap.get("supplierId")));
		return productsuppliersId;
		
	}	
    
    
}


