import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { CatSucursalComponent } from './cat-sucursal.component';
import { CatSucursalDetailComponent } from './cat-sucursal-detail.component';
import { CatSucursalUpdateComponent } from './cat-sucursal-update.component';
import { CatSucursalDeleteDialogComponent } from './cat-sucursal-delete-dialog.component';
import { catSucursalRoute } from './cat-sucursal.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(catSucursalRoute)],
  declarations: [CatSucursalComponent, CatSucursalDetailComponent, CatSucursalUpdateComponent, CatSucursalDeleteDialogComponent],
  entryComponents: [CatSucursalDeleteDialogComponent],
})
export class ResaMxWebCatSucursalModule {}
