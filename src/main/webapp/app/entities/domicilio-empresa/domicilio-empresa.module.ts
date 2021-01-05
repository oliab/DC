import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { DomicilioEmpresaComponent } from './domicilio-empresa.component';
import { DomicilioEmpresaDetailComponent } from './domicilio-empresa-detail.component';
import { DomicilioEmpresaUpdateComponent } from './domicilio-empresa-update.component';
import { DomicilioEmpresaDeleteDialogComponent } from './domicilio-empresa-delete-dialog.component';
import { domicilioEmpresaRoute } from './domicilio-empresa.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(domicilioEmpresaRoute)],
  declarations: [
    DomicilioEmpresaComponent,
    DomicilioEmpresaDetailComponent,
    DomicilioEmpresaUpdateComponent,
    DomicilioEmpresaDeleteDialogComponent,
  ],
  entryComponents: [DomicilioEmpresaDeleteDialogComponent],
})
export class ResaMxWebDomicilioEmpresaModule {}
