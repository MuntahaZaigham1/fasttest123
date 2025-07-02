
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { ProductsuppliersDetailsComponent, ProductsuppliersListComponent, ProductsuppliersNewComponent } from './';

const routes: Routes = [
	// { path: '', component: ProductsuppliersListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: ProductsuppliersDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: ProductsuppliersNewComponent },
];

export const productsuppliersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);