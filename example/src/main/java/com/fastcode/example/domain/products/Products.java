package com.fastcode.example.domain.products;
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
import com.fastcode.example.domain.orderitems.Orderitems;
import com.fastcode.example.domain.productsuppliers.Productsuppliers;
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
@Config(defaultVariableName = "productsEntity")
@Table(name = "products")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Products extends AbstractEntity {

    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Basic
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Orderitems> orderitemsSet = new HashSet<Orderitems>();
    
    public void addOrderitems(Orderitems orderitems) {        
    	orderitemsSet.add(orderitems);

        orderitems.setProducts(this);
    }
    public void removeOrderitems(Orderitems orderitems) {
        orderitemsSet.remove(orderitems);
        
        orderitems.setProducts(null);
    }
    
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Productsuppliers> productsuppliersSet = new HashSet<Productsuppliers>();
    
    public void addProductsuppliers(Productsuppliers productsuppliers) {        
    	productsuppliersSet.add(productsuppliers);

        productsuppliers.setProducts(this);
    }
    public void removeProductsuppliers(Productsuppliers productsuppliers) {
        productsuppliersSet.remove(productsuppliers);
        
        productsuppliers.setProducts(null);
    }
    

}


