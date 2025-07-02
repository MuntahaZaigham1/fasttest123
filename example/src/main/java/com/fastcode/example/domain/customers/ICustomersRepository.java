package com.fastcode.example.domain.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.fastcode.example.domain.orders.Orders;
import java.util.*;
import com.fastcode.example.domain.customers.Customers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("customersRepository")
public interface ICustomersRepository extends JpaRepository<Customers, Integer>,QuerydslPredicateExecutor<Customers> {

    
}
