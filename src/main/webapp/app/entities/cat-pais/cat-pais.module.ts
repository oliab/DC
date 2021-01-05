import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatPaisComponent } from './cat-pais.component';
import { CatPaisDetailComponent } from './cat-pais-detail.component';
import { CatPaisUpdateComponent } from './cat-pais-update.component';
import { CatPaisDeleteDialogComponent } from './cat-pais-delete-dialog.component';
import { catPaisRoute } from './cat-pais.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catPaisRoute)],
  declarations: [CatPaisComponent, CatPaisDetailComponent, CatPaisUpdateComponent, CatPaisDeleteDialogComponent],
  entryComponents: [CatPaisDeleteDialogComponent],
})
export class ResaMxWebCatPaisModule {}
