import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatIdentificacionComponent } from './cat-identificacion.component';
import { CatIdentificacionDetailComponent } from './cat-identificacion-detail.component';
import { CatIdentificacionUpdateComponent } from './cat-identificacion-update.component';
import { CatIdentificacionDeleteDialogComponent } from './cat-identificacion-delete-dialog.component';
import { catIdentificacionRoute } from './cat-identificacion.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catIdentificacionRoute)],
  declarations: [
    CatIdentificacionComponent,
    CatIdentificacionDetailComponent,
    CatIdentificacionUpdateComponent,
    CatIdentificacionDeleteDialogComponent,
  ],
  entryComponents: [CatIdentificacionDeleteDialogComponent],
})
export class ResaMxWebCatIdentificacionModule {}
