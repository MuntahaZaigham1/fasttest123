package com.fastcode.example.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.fastcode.example.domain.orderitems.Orderitems;
import java.util.*;
import com.fastcode.example.domain.orders.Orders;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("ordersRepository")
public interface IOrdersRepository extends JpaRepository<Orders, Integer>,QuerydslPredicateExecutor<Orders> {

    
}
