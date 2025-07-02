package com.fastcode.example.application.orders.dto;

import java.time.*;
import java.util.UUID;
import java.util.Date;
import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter
public class CreateOrdersOutput {

    private LocalDateTime orderDate;
    private Integer orderId;
    private String status;
	private Integer customerId;
	private String customersDescriptiveField;

}
