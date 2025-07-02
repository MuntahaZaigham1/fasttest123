package com.fastcode.example.application.orders.dto;

import java.time.*;
import java.util.UUID;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrdersInput {

  	private LocalDateTime orderDate;
  
  	@NotNull(message = "status Should not be null")
  	@Length(max = 20, message = "status must be less than 20 characters")
  	private String status;
  
  	private Integer customerId;

}
