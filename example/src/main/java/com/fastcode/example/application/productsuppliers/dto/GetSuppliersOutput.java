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
public class GetSuppliersOutput {

 	private String contactEmail;
 	private String name;
 	private Integer supplierId;
  	private Integer productsuppliersProductId;
  	private Integer productsuppliersSupplierId;

}
