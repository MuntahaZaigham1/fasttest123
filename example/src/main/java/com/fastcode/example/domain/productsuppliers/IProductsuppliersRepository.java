package com.fastcode.example.domain.productsuppliers;

import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("productsuppliersRepository")
public interface IProductsuppliersRepository extends JpaRepository<Productsuppliers, ProductsuppliersId>,QuerydslPredicateExecutor<Productsuppliers> {

    
}
