package com.fastcode.example.application.orderitems.dto;

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
public class GetOrdersOutput {

 	private LocalDateTime orderDate;
 	private Integer orderId;
 	private String status;
  	private Integer orderitemsOrderItemId;

}
