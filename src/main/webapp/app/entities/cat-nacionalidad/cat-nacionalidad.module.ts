import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatNacionalidadComponent } from './cat-nacionalidad.component';
import { CatNacionalidadDetailComponent } from './cat-nacionalidad-detail.component';
import { CatNacionalidadUpdateComponent } from './cat-nacionalidad-update.component';
import { CatNacionalidadDeleteDialogComponent } from './cat-nacionalidad-delete-dialog.component';
import { catNacionalidadRoute } from './cat-nacionalidad.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catNacionalidadRoute)],
  declarations: [
    CatNacionalidadComponent,
    CatNacionalidadDetailComponent,
    CatNacionalidadUpdateComponent,
    CatNacionalidadDeleteDialogComponent,
  ],
  entryComponents: [CatNacionalidadDeleteDialogComponent],
})
export class ResaMxWebCatNacionalidadModule {}
