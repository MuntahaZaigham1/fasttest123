package com.fastcode.example.application.products.dto;

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
public class UpdateProductsOutput {

  	private String name;
  	private BigDecimal price;
  	private Integer productId;
  	private Integer stock;

}