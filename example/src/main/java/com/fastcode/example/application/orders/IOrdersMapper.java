package com.fastcode.example.application.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.customers.Customers;
import com.fastcode.example.application.orders.dto.*;
import com.fastcode.example.domain.orders.Orders;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrdersMapper {
   Orders createOrdersInputToOrders(CreateOrdersInput ordersDto);
   
   @Mappings({ 
   @Mapping(source = "entity.customers.customerId", target = "customerId"),                   
   @Mapping(source = "entity.customers.name", target = "customersDescriptiveField"),                    
   }) 
   CreateOrdersOutput ordersToCreateOrdersOutput(Orders entity);
   
    Orders updateOrdersInputToOrders(UpdateOrdersInput ordersDto);
    
    @Mappings({ 
    @Mapping(source = "entity.customers.customerId", target = "customerId"),                   
    @Mapping(source = "entity.customers.name", target = "customersDescriptiveField"),                    
   	}) 
   	UpdateOrdersOutput ordersToUpdateOrdersOutput(Orders entity);
   	@Mappings({ 
   	@Mapping(source = "entity.customers.customerId", target = "customerId"),                   
   	@Mapping(source = "entity.customers.name", target = "customersDescriptiveField"),                    
   	}) 
   	FindOrdersByIdOutput ordersToFindOrdersByIdOutput(Orders entity);


   @Mappings({
   @Mapping(source = "foundOrders.orderId", target = "ordersOrderId"),
   })
   GetCustomersOutput customersToGetCustomersOutput(Customers customers, Orders foundOrders);
   
}
