
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { CustomersDetailsComponent, CustomersListComponent, CustomersNewComponent } from './';

const routes: Routes = [
	// { path: '', component: CustomersListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: CustomersDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: CustomersNewComponent },
];

export const customersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);