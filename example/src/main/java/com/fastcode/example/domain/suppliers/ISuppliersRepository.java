package com.fastcode.example.domain.suppliers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
import java.util.*;
import com.fastcode.example.domain.suppliers.Suppliers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("suppliersRepository")
public interface ISuppliersRepository extends JpaRepository<Suppliers, Integer>,QuerydslPredicateExecutor<Suppliers> {

    
}
