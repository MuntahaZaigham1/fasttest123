
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from 'src/app/core/guards/auth.guard';


const routes: Routes = [
	/* 
	{
		path: 'rolepermission',
		loadChildren: () => import('./user-management/rolepermission/rolepermission.module').then(m => m.RolepermissionModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'role',
		loadChildren: () => import('./user-management/role/role.module').then(m => m.RoleModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'userpermission',
		loadChildren: () => import('./user-management/userpermission/userpermission.module').then(m => m.UserpermissionModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'permission',
		loadChildren: () => import('./user-management/permission/permission.module').then(m => m.PermissionModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'userrole',
		loadChildren: () => import('./user-management/userrole/userrole.module').then(m => m.UserroleModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'user',
		loadChildren: () => import('./user-management/user/user.module').then(m => m.UserModule),
		canActivate: [AuthGuard]
	},
	*/ 
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forChild(routes);