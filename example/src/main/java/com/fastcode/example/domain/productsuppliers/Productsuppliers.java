package com.fastcode.example.domain.productsuppliers;
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
import com.fastcode.example.domain.productsuppliers.ProductsuppliersId;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.domain.suppliers.Suppliers;
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
@Config(defaultVariableName = "productsuppliersEntity")
@Table(name = "productsuppliers")
@IdClass(ProductsuppliersId.class)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Productsuppliers extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    private Products products;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "supplier_id", insertable=false, updatable=false)
    private Suppliers suppliers;


}


