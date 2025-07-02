
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { SwaggerComponent } from './core/swagger/swagger.component';
import { ErrorPageComponent  } from './core/error-page/error-page.component';

const routes: Routes = [
	{
		path: '',
		loadChildren: () => import('./extended/core/core.module').then(m => m.CoreExtendedModule),
	},
  	{ path: "swagger-ui", component: SwaggerComponent },
	{
		path: 'orderitems',
		loadChildren: () => import('./extended/entities/orderitems/orderitems.module').then(m => m.OrderitemsExtendedModule),

	},
	{
		path: 'productsuppliers',
		loadChildren: () => import('./extended/entities/productsuppliers/productsuppliers.module').then(m => m.ProductsuppliersExtendedModule),

	},
	{
		path: 'suppliers',
		loadChildren: () => import('./extended/entities/suppliers/suppliers.module').then(m => m.SuppliersExtendedModule),

	},
	{
		path: 'orders',
		loadChildren: () => import('./extended/entities/orders/orders.module').then(m => m.OrdersExtendedModule),

	},
	{
		path: 'customers',
		loadChildren: () => import('./extended/entities/customers/customers.module').then(m => m.CustomersExtendedModule),

	},
	{
		path: 'products',
		loadChildren: () => import('./extended/entities/products/products.module').then(m => m.ProductsExtendedModule),

	},
	{ path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forRoot(routes);