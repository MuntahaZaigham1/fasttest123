package com.fastcode.example.application.products.dto;

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
public class UpdateProductsInput {

  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  	
  	@NotNull(message = "productId Should not be null")
  	private Integer productId;
  	
  	@NotNull(message = "stock Should not be null")
  	private Integer stock;
  	
  	private Long versiono;
  
}
