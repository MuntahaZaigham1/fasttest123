package com.fastcode.example.application.productsuppliers.dto;

import java.util.Date;
import java.util.UUID;
import java.io.File;
import java.util.List;
import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
public class GetProductsOutput {

 	private String name;
 	private BigDecimal price;
 	private Integer productId;
 	private Integer stock;
  	private Integer productsuppliersProductId;
  	private Integer productsuppliersSupplierId;

}
