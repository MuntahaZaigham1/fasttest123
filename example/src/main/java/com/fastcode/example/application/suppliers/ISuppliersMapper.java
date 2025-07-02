package com.fastcode.example.application.suppliers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.application.suppliers.dto.*;
import com.fastcode.example.domain.suppliers.Suppliers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ISuppliersMapper {
   Suppliers createSuppliersInputToSuppliers(CreateSuppliersInput suppliersDto);
   CreateSuppliersOutput suppliersToCreateSuppliersOutput(Suppliers entity);
   
    Suppliers updateSuppliersInputToSuppliers(UpdateSuppliersInput suppliersDto);
    
   	UpdateSuppliersOutput suppliersToUpdateSuppliersOutput(Suppliers entity);
   	FindSuppliersByIdOutput suppliersToFindSuppliersByIdOutput(Suppliers entity);


}
