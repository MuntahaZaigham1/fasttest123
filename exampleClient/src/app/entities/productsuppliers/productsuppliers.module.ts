import { NgModule } from '@angular/core';

import { ProductsuppliersDetailsComponent, ProductsuppliersListComponent, ProductsuppliersNewComponent} from './';
import { productsuppliersRoute } from './productsuppliers.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    ProductsuppliersDetailsComponent, ProductsuppliersListComponent,ProductsuppliersNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    productsuppliersRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class ProductsuppliersModule {
}
