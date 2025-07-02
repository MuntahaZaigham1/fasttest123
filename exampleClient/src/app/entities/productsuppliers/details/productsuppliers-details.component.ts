import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { ProductsuppliersService } from '../productsuppliers.service';
import { IProductsuppliers
 } from '../iproductsuppliers';
import { BaseDetailsComponent, FieldType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { ProductsService } from 'src/app/entities/products/products.service';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';

@Component({
  selector: 'app-productsuppliers-details',
  templateUrl: './productsuppliers-details.component.html',
  styleUrls: ['./productsuppliers-details.component.scss']
})
export class ProductsuppliersDetailsComponent extends BaseDetailsComponent<IProductsuppliers> implements OnInit {
	title = 'Productsuppliers';
	parentUrl = 'productsuppliers';
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public productsuppliersService: ProductsuppliersService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public productsService: ProductsService,
		public suppliersService: SuppliersService,
	) {
		super(formBuilder, router, route, dialog, pickerDialogService, productsuppliersService, errorService);
  }

	ngOnInit() {
		this.entityName = 'Productsuppliers';
		this.setAssociations();
		super.ngOnInit();
		this.setForm();
    	this.getItem();
	}
  
  setForm(){
    this.itemForm = this.formBuilder.group({
      productId: ['', Validators.required],
      supplierId: ['', Validators.required],
      productsDescriptiveField : [''],
      suppliersDescriptiveField : [''],
      
    });
    
    this.fields = [
      ];
      
  }
  
  onItemFetched(item: IProductsuppliers) {
    this.item = item;
     this.itemForm.patchValue(item);

  }
  
  setAssociations(){
    this.associations = [
      {
        column: [
	        {
	          key: 'productId',
	          value: undefined,
	          referencedkey: 'productId'
			},
		],
		isParent: false,
		table: 'products',
		type: 'ManyToOne',
		label: 'products',
		service: this.productsService,
		descriptiveField: 'productsDescriptiveField',
	    referencedDescriptiveField: 'productId',
		},
      {
        column: [
	        {
	          key: 'supplierId',
	          value: undefined,
	          referencedkey: 'supplierId'
			},
		],
		isParent: false,
		table: 'suppliers',
		type: 'ManyToOne',
		label: 'suppliers',
		service: this.suppliersService,
		descriptiveField: 'suppliersDescriptiveField',
	    referencedDescriptiveField: 'supplierId',
		},
		];
		
		this.childAssociations = this.associations.filter(association => {
			return (association.isParent);
		});

		this.parentAssociations = this.associations.filter(association => {
			return (!association.isParent);
		});
	}
	
	onSubmit() {
		let productsuppliers = this.itemForm.getRawValue();



        super.onSubmit(productsuppliers);
	}
}
