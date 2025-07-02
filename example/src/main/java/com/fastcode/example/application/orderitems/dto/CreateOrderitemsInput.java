package com.fastcode.example.application.orderitems.dto;

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
public class CreateOrderitemsInput {

  	@NotNull(message = "priceAtOrderTime Should not be null")
  	private BigDecimal priceAtOrderTime;
  
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  
  	private Integer orderId;
  	private Integer productId;

}
