import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute,Router} from "@angular/router";
import { FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProductsuppliersService } from '../productsuppliers.service';
 import { IProductsuppliers
 } from '../iproductsuppliers';
import { BaseNewComponent, FieldType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { ProductsService } from 'src/app/entities/products/products.service';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';

@Component({
  selector: 'app-productsuppliers-new',
  templateUrl: './productsuppliers-new.component.html',
  styleUrls: ['./productsuppliers-new.component.scss']
})
export class ProductsuppliersNewComponent extends BaseNewComponent<IProductsuppliers> implements OnInit {
  
    title:string = "New Productsuppliers";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<ProductsuppliersNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public productsuppliersService: ProductsuppliersService,
		public errorService: ErrorService,
		public productsService: ProductsService,
		public suppliersService: SuppliersService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, productsuppliersService, errorService);
	}
 
		ngOnInit() {
		this.entityName = 'Productsuppliers';
		this.setAssociations();
		super.ngOnInit();
    	this.setForm();
		this.checkPassedData();
        }


	setForm(){
 		this.itemForm = this.formBuilder.group({
      productId: ['', Validators.required],
      supplierId: ['', Validators.required],
      productsDescriptiveField: [''],
      suppliersDescriptiveField: [''],
    });
    
    this.fields = [
		];
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
				service: this.productsService,
				label: 'products',
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
				service: this.suppliersService,
				label: 'suppliers',
				descriptiveField: 'suppliersDescriptiveField',
				referencedDescriptiveField: 'supplierId',
		    
			},
		];
		this.parentAssociations = this.associations.filter(association => {
			return (!association.isParent);
		});

	}
	
	onSubmit() {
		let productsuppliers = this.itemForm.getRawValue();



        super.onSubmit(productsuppliers);

	}
    
}
