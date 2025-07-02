package com.fastcode.example.domain.productsuppliers;

import java.time.*;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter
@NoArgsConstructor
public class ProductsuppliersId implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer productId;
    private Integer supplierId;
    
    public ProductsuppliersId(Integer productId,Integer supplierId) {
 	this.productId = productId;
 	this.supplierId = supplierId;
    }
    
}