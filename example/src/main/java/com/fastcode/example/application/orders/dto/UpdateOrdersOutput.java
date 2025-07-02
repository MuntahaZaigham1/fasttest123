package com.fastcode.example.application.orders.dto;

import java.time.*;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.io.File;
import java.util.List;

@Getter @Setter
public class UpdateOrdersOutput {

  	private LocalDateTime orderDate;
  	private Integer orderId;
  	private String status;
  	private Integer customerId;
	private String customersDescriptiveField;

}