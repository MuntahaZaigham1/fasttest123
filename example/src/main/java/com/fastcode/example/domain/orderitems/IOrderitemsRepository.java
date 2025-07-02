package com.fastcode.example.domain.orderitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.fastcode.example.domain.orderitems.Orderitems;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Repository("orderitemsRepository")
public interface IOrderitemsRepository extends JpaRepository<Orderitems, Integer>,QuerydslPredicateExecutor<Orderitems> {

    
}
