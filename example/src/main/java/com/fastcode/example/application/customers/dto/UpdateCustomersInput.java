package com.fastcode.example.application.customers.dto;

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
public class UpdateCustomersInput {

  	private LocalDateTime createdAt;
  	
  	@NotNull(message = "customerId Should not be null")
  	private Integer customerId;
  	
  	@NotNull(message = "email Should not be null")
 	@Length(max = 100, message = "email must be less than 100 characters")
  	private String email;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	private Long versiono;
  
}
