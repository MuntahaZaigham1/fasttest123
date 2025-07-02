package com.fastcode.example.application.customers.dto;

import java.time.*;
import java.util.UUID;
import java.util.Date;
import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class FindCustomersByIdOutput {

    private LocalDateTime createdAt;

    private Integer customerId;

    private String name;

    private Long versiono;
}
