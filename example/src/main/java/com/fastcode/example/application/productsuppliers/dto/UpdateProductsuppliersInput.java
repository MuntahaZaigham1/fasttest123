package com.fastcode.example.application.productsuppliers.dto;

import java.time.*;
import java.util.Date;
import java.util.UUID;
import java.io.File;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
public class UpdateProductsuppliersInput {

  	@NotNull(message = "productId Should not be null")
  	private Integer productId;
  	
  	@NotNull(message = "supplierId Should not be null")
  	private Integer supplierId;
  	
  	private Long versiono;
  
}
