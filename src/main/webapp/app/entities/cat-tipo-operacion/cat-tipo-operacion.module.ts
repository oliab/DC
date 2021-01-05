import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatTipoOperacionComponent } from './cat-tipo-operacion.component';
import { CatTipoOperacionDetailComponent } from './cat-tipo-operacion-detail.component';
import { CatTipoOperacionUpdateComponent } from './cat-tipo-operacion-update.component';
import { CatTipoOperacionDeleteDialogComponent } from './cat-tipo-operacion-delete-dialog.component';
import { catTipoOperacionRoute } from './cat-tipo-operacion.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catTipoOperacionRoute)],
  declarations: [
    CatTipoOperacionComponent,
    CatTipoOperacionDetailComponent,
    CatTipoOperacionUpdateComponent,
    CatTipoOperacionDeleteDialogComponent,
  ],
  entryComponents: [CatTipoOperacionDeleteDialogComponent],
})
export class ResaMxWebCatTipoOperacionModule {}
