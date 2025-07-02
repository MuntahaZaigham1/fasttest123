package com.fastcode.example.application.orderitems.dto;

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
public class UpdateOrderitemsInput {

  	@NotNull(message = "orderItemId Should not be null")
  	private Integer orderItemId;
  	
  	@NotNull(message = "priceAtOrderTime Should not be null")
  	private BigDecimal priceAtOrderTime;
  	
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  	
  	private Integer orderId;
  	private Integer productId;
  	private Long versiono;
  
}
