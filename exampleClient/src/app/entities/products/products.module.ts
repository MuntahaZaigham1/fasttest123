import { NgModule } from '@angular/core';

import { ProductsDetailsComponent, ProductsListComponent, ProductsNewComponent} from './';
import { productsRoute } from './products.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    ProductsDetailsComponent, ProductsListComponent,ProductsNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    productsRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class ProductsModule {
}
