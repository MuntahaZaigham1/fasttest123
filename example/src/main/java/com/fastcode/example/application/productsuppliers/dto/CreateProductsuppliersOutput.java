package com.fastcode.example.application.productsuppliers.dto;

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
public class CreateProductsuppliersOutput {

    private Integer productId;
    private Integer supplierId;
	private Integer productsDescriptiveField;
	private Integer suppliersDescriptiveField;

}
