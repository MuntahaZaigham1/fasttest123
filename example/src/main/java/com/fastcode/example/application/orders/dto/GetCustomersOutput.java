package com.fastcode.example.application.orders.dto;

import java.util.Date;
import java.util.UUID;
import java.io.File;
import java.util.List;
import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
public class GetCustomersOutput {

 	private LocalDateTime createdAt;
 	private Integer customerId;
 	private String email;
 	private String name;
  	private Integer ordersOrderId;

}
