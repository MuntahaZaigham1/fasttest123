package com.fastcode.example.application.suppliers.dto;

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
public class UpdateSuppliersInput {

 	@Length(max = 100, message = "contactEmail must be less than 100 characters")
  	private String contactEmail;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	@NotNull(message = "supplierId Should not be null")
  	private Integer supplierId;
  	
  	private Long versiono;
  
}
