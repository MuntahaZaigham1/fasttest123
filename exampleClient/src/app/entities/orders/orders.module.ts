import { NgModule } from '@angular/core';

import { OrdersDetailsComponent, OrdersListComponent, OrdersNewComponent} from './';
import { ordersRoute } from './orders.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    OrdersDetailsComponent, OrdersListComponent,OrdersNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    ordersRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class OrdersModule {
}
