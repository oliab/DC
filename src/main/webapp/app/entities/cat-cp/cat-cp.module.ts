import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatCPComponent } from './cat-cp.component';
import { CatCPDetailComponent } from './cat-cp-detail.component';
import { CatCPUpdateComponent } from './cat-cp-update.component';
import { CatCPDeleteDialogComponent } from './cat-cp-delete-dialog.component';
import { catCPRoute } from './cat-cp.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catCPRoute)],
  declarations: [CatCPComponent, CatCPDetailComponent, CatCPUpdateComponent, CatCPDeleteDialogComponent],
  entryComponents: [CatCPDeleteDialogComponent],
})
export class ResaMxWebCatCPModule {}
