import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatRiesgoComponent } from './cat-riesgo.component';
import { CatRiesgoDetailComponent } from './cat-riesgo-detail.component';
import { CatRiesgoUpdateComponent } from './cat-riesgo-update.component';
import { CatRiesgoDeleteDialogComponent } from './cat-riesgo-delete-dialog.component';
import { catRiesgoRoute } from './cat-riesgo.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catRiesgoRoute)],
  declarations: [CatRiesgoComponent, CatRiesgoDetailComponent, CatRiesgoUpdateComponent, CatRiesgoDeleteDialogComponent],
  entryComponents: [CatRiesgoDeleteDialogComponent],
})
export class ResaMxWebCatRiesgoModule {}
