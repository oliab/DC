import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatTipoEmpresaComponent } from './cat-tipo-empresa.component';
import { CatTipoEmpresaDetailComponent } from './cat-tipo-empresa-detail.component';
import { CatTipoEmpresaUpdateComponent } from './cat-tipo-empresa-update.component';
import { CatTipoEmpresaDeleteDialogComponent } from './cat-tipo-empresa-delete-dialog.component';
import { catTipoEmpresaRoute } from './cat-tipo-empresa.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catTipoEmpresaRoute)],
  declarations: [
    CatTipoEmpresaComponent,
    CatTipoEmpresaDetailComponent,
    CatTipoEmpresaUpdateComponent,
    CatTipoEmpresaDeleteDialogComponent,
  ],
  entryComponents: [CatTipoEmpresaDeleteDialogComponent],
})
export class ResaMxWebCatTipoEmpresaModule {}
