
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { OrderitemsDetailsComponent, OrderitemsListComponent, OrderitemsNewComponent } from './';

const routes: Routes = [
	// { path: '', component: OrderitemsListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: OrderitemsDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: OrderitemsNewComponent },
];

export const orderitemsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);