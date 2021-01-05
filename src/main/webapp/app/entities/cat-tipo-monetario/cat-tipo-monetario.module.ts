import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatTipoMonetarioComponent } from './cat-tipo-monetario.component';
import { CatTipoMonetarioDetailComponent } from './cat-tipo-monetario-detail.component';
import { CatTipoMonetarioUpdateComponent } from './cat-tipo-monetario-update.component';
import { CatTipoMonetarioDeleteDialogComponent } from './cat-tipo-monetario-delete-dialog.component';
import { catTipoMonetarioRoute } from './cat-tipo-monetario.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catTipoMonetarioRoute)],
  declarations: [
    CatTipoMonetarioComponent,
    CatTipoMonetarioDetailComponent,
    CatTipoMonetarioUpdateComponent,
    CatTipoMonetarioDeleteDialogComponent,
  ],
  entryComponents: [CatTipoMonetarioDeleteDialogComponent],
})
export class ResaMxWebCatTipoMonetarioModule {}
