import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatSectorComponent } from './cat-sector.component';
import { CatSectorDetailComponent } from './cat-sector-detail.component';
import { CatSectorUpdateComponent } from './cat-sector-update.component';
import { CatSectorDeleteDialogComponent } from './cat-sector-delete-dialog.component';
import { catSectorRoute } from './cat-sector.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catSectorRoute)],
  declarations: [CatSectorComponent, CatSectorDetailComponent, CatSectorUpdateComponent, CatSectorDeleteDialogComponent],
  entryComponents: [CatSectorDeleteDialogComponent],
})
export class ResaMxWebCatSectorModule {}
