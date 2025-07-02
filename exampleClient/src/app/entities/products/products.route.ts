
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { ProductsDetailsComponent, ProductsListComponent, ProductsNewComponent } from './';

const routes: Routes = [
	// { path: '', component: ProductsListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: ProductsDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: ProductsNewComponent },
];

export const productsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);