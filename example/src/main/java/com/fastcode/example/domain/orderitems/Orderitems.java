package com.fastcode.example.domain.orderitems;
import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.io.File;
import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.abstractentity.AbstractEntity;
import com.fastcode.example.domain.ByteArrayConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

@Entity
@Config(defaultVariableName = "orderitemsEntity")
@Table(name = "orderitems")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Orderitems extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Integer orderItemId;
    
    @Basic
    @Column(name = "price_at_order_time", nullable = false)
    private BigDecimal priceAtOrderTime;
    
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;


}


