package com.fastcode.example.application.productsuppliers.dto;

import java.time.*;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.io.File;
import java.util.List;

@Getter @Setter
public class UpdateProductsuppliersOutput {

  	private Integer productId;
  	private Integer supplierId;
	private Integer productsDescriptiveField;
	private Integer suppliersDescriptiveField;

}