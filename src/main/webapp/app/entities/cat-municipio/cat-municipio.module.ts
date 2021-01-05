import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatMunicipioComponent } from './cat-municipio.component';
import { CatMunicipioDetailComponent } from './cat-municipio-detail.component';
import { CatMunicipioUpdateComponent } from './cat-municipio-update.component';
import { CatMunicipioDeleteDialogComponent } from './cat-municipio-delete-dialog.component';
import { catMunicipioRoute } from './cat-municipio.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catMunicipioRoute)],
  declarations: [CatMunicipioComponent, CatMunicipioDetailComponent, CatMunicipioUpdateComponent, CatMunicipioDeleteDialogComponent],
  entryComponents: [CatMunicipioDeleteDialogComponent],
})
export class ResaMxWebCatMunicipioModule {}
