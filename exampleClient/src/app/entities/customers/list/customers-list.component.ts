import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ICustomers } from '../icustomers';
import { CustomersService } from '../customers.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CustomersNewComponent } from '../new/customers-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';


@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.scss']
})
export class CustomersListComponent extends BaseListComponent<ICustomers> implements OnInit {

	title = 'Customers';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public customersService: CustomersService,
		public errorService: ErrorService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, customersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Customers';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['customerId', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'createdAt',
				searchColumn: 'createdAt',
				label: 'created At',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
    		{
				column: 'customerId',
				searchColumn: 'customerId',
				label: 'customer Id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'email',
				searchColumn: 'email',
				label: 'email',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'name',
				searchColumn: 'name',
				label: 'name',
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
		comp = CustomersNewComponent;
	}
	super.addNew(comp);
  }
  
}
