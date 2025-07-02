import { NgModule } from '@angular/core';

import { SuppliersDetailsComponent, SuppliersListComponent, SuppliersNewComponent} from './';
import { suppliersRoute } from './suppliers.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    SuppliersDetailsComponent, SuppliersListComponent,SuppliersNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    suppliersRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class SuppliersModule {
}
