package com.fastcode.example.application.orders.dto;

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
public class UpdateOrdersInput {

  	private LocalDateTime orderDate;
  	
  	@NotNull(message = "orderId Should not be null")
  	private Integer orderId;
  	
  	@NotNull(message = "status Should not be null")
 	@Length(max = 20, message = "status must be less than 20 characters")
  	private String status;
  	
  	private Integer customerId;
  	private Long versiono;
  
}
