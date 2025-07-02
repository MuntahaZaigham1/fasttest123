package com.fastcode.example.application.orderitems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.orders.Orders;
import com.fastcode.example.domain.products.Products;
import com.fastcode.example.application.orderitems.dto.*;
import com.fastcode.example.domain.orderitems.Orderitems;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrderitemsMapper {
   Orderitems createOrderitemsInputToOrderitems(CreateOrderitemsInput orderitemsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.orders.orderId", target = "orderId"),                   
   @Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
   @Mapping(source = "entity.products.productId", target = "productId"),                   
   @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   }) 
   CreateOrderitemsOutput orderitemsToCreateOrderitemsOutput(Orderitems entity);
   
    Orderitems updateOrderitemsInputToOrderitems(UpdateOrderitemsInput orderitemsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.orders.orderId", target = "orderId"),                   
    @Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
    @Mapping(source = "entity.products.productId", target = "productId"),                   
    @Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	}) 
   	UpdateOrderitemsOutput orderitemsToUpdateOrderitemsOutput(Orderitems entity);
   	@Mappings({ 
   	@Mapping(source = "entity.orders.orderId", target = "orderId"),                   
   	@Mapping(source = "entity.orders.orderId", target = "ordersDescriptiveField"),                    
   	@Mapping(source = "entity.products.productId", target = "productId"),                   
   	@Mapping(source = "entity.products.productId", target = "productsDescriptiveField"),                    
   	}) 
   	FindOrderitemsByIdOutput orderitemsToFindOrderitemsByIdOutput(Orderitems entity);


   @Mappings({
   @Mapping(source = "foundOrderitems.orderItemId", target = "orderitemsOrderItemId"),
   })
   GetOrdersOutput ordersToGetOrdersOutput(Orders orders, Orderitems foundOrderitems);
   
   @Mappings({
   @Mapping(source = "foundOrderitems.orderItemId", target = "orderitemsOrderItemId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, Orderitems foundOrderitems);
   
}
