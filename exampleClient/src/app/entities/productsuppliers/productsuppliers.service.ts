
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProductsuppliers } from './iproductsuppliers';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class ProductsuppliersService extends GenericApiService<IProductsuppliers> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "productsuppliers");
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
