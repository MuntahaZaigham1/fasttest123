import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IOrders } from '../iorders';
import { OrdersService } from '../orders.service';
import { Router, ActivatedRoute } from '@angular/router';
import { OrdersNewComponent } from '../new/orders-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { CustomersService } from 'src/app/entities/customers/customers.service';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.scss']
})
export class OrdersListComponent extends BaseListComponent<IOrders> implements OnInit {

	title = 'Orders';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public ordersService: OrdersService,
		public errorService: ErrorService,
		public customersService: CustomersService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, ordersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Orders';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['orderId', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
			{
				column: [
            {
					  	key: 'customerId',
					  	value: undefined,
					  	referencedkey: 'customerId'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'customersDescriptiveField',
				referencedDescriptiveField: 'name',
				service: this.customersService,
				associatedObj: undefined,
				table: 'customers',
				type: 'ManyToOne',
				url: 'orders',
				listColumn: 'customers',
				label: 'customers',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'orderDate',
				searchColumn: 'orderDate',
				label: 'order Date',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
    		{
				column: 'orderId',
				searchColumn: 'orderId',
				label: 'order Id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'status',
				searchColumn: 'status',
				label: 'status',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
			{
	  			column: 'customersDescriptiveField',
				searchColumn: 'customers',
				label: 'customers',
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
		comp = OrdersNewComponent;
	}
	super.addNew(comp);
  }
  
}
