import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatLocalidadComponent } from './cat-localidad.component';
import { CatLocalidadDetailComponent } from './cat-localidad-detail.component';
import { CatLocalidadUpdateComponent } from './cat-localidad-update.component';
import { CatLocalidadDeleteDialogComponent } from './cat-localidad-delete-dialog.component';
import { catLocalidadRoute } from './cat-localidad.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catLocalidadRoute)],
  declarations: [CatLocalidadComponent, CatLocalidadDetailComponent, CatLocalidadUpdateComponent, CatLocalidadDeleteDialogComponent],
  entryComponents: [CatLocalidadDeleteDialogComponent],
})
export class ResaMxWebCatLocalidadModule {}
