import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IOrderitems } from '../iorderitems';
import { OrderitemsService } from '../orderitems.service';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderitemsNewComponent } from '../new/orderitems-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { OrdersService } from 'src/app/entities/orders/orders.service';
import { ProductsService } from 'src/app/entities/products/products.service';

@Component({
  selector: 'app-orderitems-list',
  templateUrl: './orderitems-list.component.html',
  styleUrls: ['./orderitems-list.component.scss']
})
export class OrderitemsListComponent extends BaseListComponent<IOrderitems> implements OnInit {

	title = 'Orderitems';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public orderitemsService: OrderitemsService,
		public errorService: ErrorService,
		public ordersService: OrdersService,
		public productsService: ProductsService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, orderitemsService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Orderitems';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['orderItemId', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
			{
				column: [
            {
					  	key: 'orderId',
					  	value: undefined,
					  	referencedkey: 'orderId'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'ordersDescriptiveField',
				referencedDescriptiveField: 'orderId',
				service: this.ordersService,
				associatedObj: undefined,
				table: 'orders',
				type: 'ManyToOne',
				url: 'orderitems',
				listColumn: 'orders',
				label: 'orders',

			},
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
				url: 'orderitems',
				listColumn: 'products',
				label: 'products',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'orderItemId',
				searchColumn: 'orderItemId',
				label: 'order Item Id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'priceAtOrderTime',
				searchColumn: 'priceAtOrderTime',
				label: 'price At Order Time',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'quantity',
				searchColumn: 'quantity',
				label: 'quantity',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
			{
	  			column: 'ordersDescriptiveField',
				searchColumn: 'orders',
				label: 'orders',
				sort: true,
				filter: true,
				type: ListColumnType.String
	  		},
			{
	  			column: 'productsDescriptiveField',
				searchColumn: 'products',
				label: 'products',
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
		comp = OrderitemsNewComponent;
	}
	super.addNew(comp);
  }
  
}
