import { NgModule } from '@angular/core';

import { CustomersDetailsComponent, CustomersListComponent, CustomersNewComponent} from './';
import { customersRoute } from './customers.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    CustomersDetailsComponent, CustomersListComponent,CustomersNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    customersRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class CustomersModule {
}
