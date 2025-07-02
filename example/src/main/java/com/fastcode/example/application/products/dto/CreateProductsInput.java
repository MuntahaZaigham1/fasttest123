package com.fastcode.example.application.products.dto;

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

@Getter
@Setter
public class CreateProductsInput {

    @NotNull(message = "name Should not be null")
    @Length(max = 100, message = "name must be less than 100 characters")
    private String name;

    @NotNull(message = "price Should not be null")
    private BigDecimal price;

    @NotNull(message = "stock Should not be null")
    private Integer stock;

    private Long newcol;
}
