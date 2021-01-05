import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatMonedaComponent } from './cat-moneda.component';
import { CatMonedaDetailComponent } from './cat-moneda-detail.component';
import { CatMonedaUpdateComponent } from './cat-moneda-update.component';
import { CatMonedaDeleteDialogComponent } from './cat-moneda-delete-dialog.component';
import { catMonedaRoute } from './cat-moneda.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catMonedaRoute)],
  declarations: [CatMonedaComponent, CatMonedaDetailComponent, CatMonedaUpdateComponent, CatMonedaDeleteDialogComponent],
  entryComponents: [CatMonedaDeleteDialogComponent],
})
export class ResaMxWebCatMonedaModule {}
