package com.fastcode.example.application.customers.dto;

import java.time.*;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.io.File;
import java.util.List;

@Getter
@Setter
public class UpdateCustomersOutput {

    private LocalDateTime createdAt;

    private Integer customerId;

    private String name;
}
