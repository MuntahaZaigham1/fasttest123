import { NgModule } from '@angular/core';

import { OrderitemsDetailsComponent, OrderitemsListComponent, OrderitemsNewComponent} from './';
import { orderitemsRoute } from './orderitems.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    OrderitemsDetailsComponent, OrderitemsListComponent,OrderitemsNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    orderitemsRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class OrderitemsModule {
}
