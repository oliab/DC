import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatTipoDocumentoComponent } from './cat-tipo-documento.component';
import { CatTipoDocumentoDetailComponent } from './cat-tipo-documento-detail.component';
import { CatTipoDocumentoUpdateComponent } from './cat-tipo-documento-update.component';
import { CatTipoDocumentoDeleteDialogComponent } from './cat-tipo-documento-delete-dialog.component';
import { catTipoDocumentoRoute } from './cat-tipo-documento.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catTipoDocumentoRoute)],
  declarations: [
    CatTipoDocumentoComponent,
    CatTipoDocumentoDetailComponent,
    CatTipoDocumentoUpdateComponent,
    CatTipoDocumentoDeleteDialogComponent,
  ],
  entryComponents: [CatTipoDocumentoDeleteDialogComponent],
})
export class ResaMxWebCatTipoDocumentoModule {}
