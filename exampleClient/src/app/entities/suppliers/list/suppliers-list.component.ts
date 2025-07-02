import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ISuppliers } from '../isuppliers';
import { SuppliersService } from '../suppliers.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SuppliersNewComponent } from '../new/suppliers-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';


@Component({
  selector: 'app-suppliers-list',
  templateUrl: './suppliers-list.component.html',
  styleUrls: ['./suppliers-list.component.scss']
})
export class SuppliersListComponent extends BaseListComponent<ISuppliers> implements OnInit {

	title = 'Suppliers';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public suppliersService: SuppliersService,
		public errorService: ErrorService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, suppliersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Suppliers';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['supplierId', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'contactEmail',
				searchColumn: 'contactEmail',
				label: 'contact Email',
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
				column: 'supplierId',
				searchColumn: 'supplierId',
				label: 'supplier Id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
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
		comp = SuppliersNewComponent;
	}
	super.addNew(comp);
  }
  
}
