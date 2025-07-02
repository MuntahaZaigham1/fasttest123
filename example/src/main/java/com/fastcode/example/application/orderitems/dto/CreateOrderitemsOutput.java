package com.fastcode.example.application.orderitems.dto;

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
public class CreateOrderitemsOutput {

    private Integer orderItemId;
    private BigDecimal priceAtOrderTime;
    private Integer quantity;
	private Integer orderId;
	private Integer ordersDescriptiveField;
	private Integer productId;
	private Integer productsDescriptiveField;

}
