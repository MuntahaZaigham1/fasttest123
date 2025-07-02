package com.fastcode.example.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.fastcode.example.domain.orderitems.Orderitems;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import java.util.*;
import com.fastcode.example.domain.products.Products;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("productsRepository")
public interface IProductsRepository extends JpaRepository<Products, Integer>,QuerydslPredicateExecutor<Products> {

    
}
