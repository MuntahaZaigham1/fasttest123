
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { OrdersDetailsComponent, OrdersListComponent, OrdersNewComponent } from './';

const routes: Routes = [
	// { path: '', component: OrdersListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: OrdersDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: OrdersNewComponent },
];

export const ordersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);