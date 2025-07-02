import { NgModule } from '@angular/core';
import { MainNavComponent } from './main-nav/main-nav.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SharedModule } from 'src/app/common/shared';
import { CoreRoutingModule } from './core.routing';

const components = [
    DashboardComponent,

    MainNavComponent
]
@NgModule({
	declarations: components,
	exports: components,
  imports: [
    SharedModule,
    CoreRoutingModule
  ]
})
export class CoreModule {
}
