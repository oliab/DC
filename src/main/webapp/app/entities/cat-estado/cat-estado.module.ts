import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatEstadoComponent } from './cat-estado.component';
import { CatEstadoDetailComponent } from './cat-estado-detail.component';
import { CatEstadoUpdateComponent } from './cat-estado-update.component';
import { CatEstadoDeleteDialogComponent } from './cat-estado-delete-dialog.component';
import { catEstadoRoute } from './cat-estado.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catEstadoRoute)],
  declarations: [CatEstadoComponent, CatEstadoDetailComponent, CatEstadoUpdateComponent, CatEstadoDeleteDialogComponent],
  entryComponents: [CatEstadoDeleteDialogComponent],
})
export class ResaMxWebCatEstadoModule {}
