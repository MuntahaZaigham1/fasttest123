import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IProductsuppliers } from '../iproductsuppliers';
import { ProductsuppliersService } from '../productsuppliers.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductsuppliersNewComponent } from '../new/productsuppliers-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { ProductsService } from 'src/app/entities/products/products.service';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';

@Component({
  selector: 'app-productsuppliers-list',
  templateUrl: './productsuppliers-list.component.html',
  styleUrls: ['./productsuppliers-list.component.scss']
})
export class ProductsuppliersListComponent extends BaseListComponent<IProductsuppliers> implements OnInit {

	title = 'Productsuppliers';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public productsuppliersService: ProductsuppliersService,
		public errorService: ErrorService,
		public productsService: ProductsService,
		public suppliersService: SuppliersService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, productsuppliersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Productsuppliers';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['productId', 'supplierId', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
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
				descriptiveField: 'productsDescriptiveField',
				referencedDescriptiveField: 'productId',
				service: this.productsService,
				associatedObj: undefined,
				table: 'products',
				type: 'ManyToOne',
				url: 'productsuppliers',
				listColumn: 'products',
				label: 'products',

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
				descriptiveField: 'suppliersDescriptiveField',
				referencedDescriptiveField: 'supplierId',
				service: this.suppliersService,
				associatedObj: undefined,
				table: 'suppliers',
				type: 'ManyToOne',
				url: 'productsuppliers',
				listColumn: 'suppliers',
				label: 'suppliers',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
			{
	  			column: 'productsDescriptiveField',
				searchColumn: 'products',
				label: 'products',
				sort: true,
				filter: true,
				type: ListColumnType.String
	  		},
			{
	  			column: 'suppliersDescriptiveField',
				searchColumn: 'suppliers',
				label: 'suppliers',
				sort: true,
				filter: true,
				type: ListColumnType.String
	  		},
		  	{
				column: 'actions',
				label: 'Actions',
				sort: false,
				filter: false,
				type: ListColumnType.String
			}
		];
		this.selectedColumns = this.columns;
		this.displayedColumns = this.columns.map((obj) => { return obj.column });
  	}
  addNew(comp: any) {
	if(!comp){
		comp = ProductsuppliersNewComponent;
	}
	super.addNew(comp);
  }
  
}
