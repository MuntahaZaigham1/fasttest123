package com.fastcode.example.application.customers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.application.customers.dto.*;
import com.fastcode.example.domain.customers.Customers;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ICustomersMapper {
   Customers createCustomersInputToCustomers(CreateCustomersInput customersDto);
   CreateCustomersOutput customersToCreateCustomersOutput(Customers entity);
   
    Customers updateCustomersInputToCustomers(UpdateCustomersInput customersDto);
    
   	UpdateCustomersOutput customersToUpdateCustomersOutput(Customers entity);
   	FindCustomersByIdOutput customersToFindCustomersByIdOutput(Customers entity);


}
