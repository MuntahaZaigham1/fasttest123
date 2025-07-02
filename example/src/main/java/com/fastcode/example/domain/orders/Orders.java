package com.fastcode.example.domain.orders;
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
import com.fastcode.example.domain.customers.Customers;
import com.fastcode.example.domain.orderitems.Orderitems;
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
@Config(defaultVariableName = "ordersEntity")
@Table(name = "orders")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Orders extends AbstractEntity {

    @Basic
    @Column(name = "order_date", nullable = true)
    private LocalDateTime orderDate;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    
    @Basic
    @Column(name = "status", nullable = false,length =20)
    private String status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Orderitems> orderitemsSet = new HashSet<Orderitems>();
    
    public void addOrderitems(Orderitems orderitems) {        
    	orderitemsSet.add(orderitems);

        orderitems.setOrders(this);
    }
    public void removeOrderitems(Orderitems orderitems) {
        orderitemsSet.remove(orderitems);
        
        orderitems.setOrders(null);
    }
    

}


