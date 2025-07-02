
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { SuppliersDetailsComponent, SuppliersListComponent, SuppliersNewComponent } from './';

const routes: Routes = [
	// { path: '', component: SuppliersListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: SuppliersDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: SuppliersNewComponent },
];

export const suppliersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);