
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { DashboardComponent } from './dashboard';

const routes: Routes = [
	{ path: '', component: DashboardComponent, pathMatch: 'full' },
	{ path: 'dashboard',  component: DashboardComponent   },
];

export const CoreRoutingModule: ModuleWithProviders<any> = RouterModule.forChild(routes);