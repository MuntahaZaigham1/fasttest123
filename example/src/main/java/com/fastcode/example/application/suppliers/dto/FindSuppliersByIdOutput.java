package com.fastcode.example.application.suppliers.dto;

import java.time.*;
import java.util.UUID;
import java.util.Date;
import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
public class FindSuppliersByIdOutput {

  	private String contactEmail;
  	private String name;
  	private Integer supplierId;
	private Long versiono;
 
}
