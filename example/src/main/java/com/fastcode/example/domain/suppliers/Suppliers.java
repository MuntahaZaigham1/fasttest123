package com.fastcode.example.domain.suppliers;
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
@Config(defaultVariableName = "suppliersEntity")
@Table(name = "suppliers")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Suppliers extends AbstractEntity {

    @Basic
    @Column(name = "contact_email", nullable = true,length =100)
    private String contactEmail;

    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;
    
    @OneToMany(mappedBy = "suppliers", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Productsuppliers> productsuppliersSet = new HashSet<Productsuppliers>();
    
    public void addProductsuppliers(Productsuppliers productsuppliers) {        
    	productsuppliersSet.add(productsuppliers);

        productsuppliers.setSuppliers(this);
    }
    public void removeProductsuppliers(Productsuppliers productsuppliers) {
        productsuppliersSet.remove(productsuppliers);
        
        productsuppliers.setSuppliers(null);
    }
    

}


