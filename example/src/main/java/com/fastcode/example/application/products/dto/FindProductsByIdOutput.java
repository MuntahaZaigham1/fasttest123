package com.fastcode.example.application.products.dto;

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
public class FindProductsByIdOutput {

    private String name;

    private BigDecimal price;

    private Integer productId;

    private Integer stock;

    private Long versiono;

    private Long newcol;
}
