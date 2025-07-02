
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IOrders } from './iorders';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class OrdersService extends GenericApiService<IOrders> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "orders");
  }
  
  convertEnumToArray(enumm:any){
      const arrayObjects = []
        // Retrieve key and values using Object.entries() method.
        for (const [propertyKey, propertyValue] of Object.entries(enumm)) {
         // Add values to array
         arrayObjects.push(propertyValue);
       }

     return arrayObjects;
   }
  
}
