import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { DomicilioUsuarioComponent } from './domicilio-usuario.component';
import { DomicilioUsuarioDetailComponent } from './domicilio-usuario-detail.component';
import { DomicilioUsuarioUpdateComponent } from './domicilio-usuario-update.component';
import { DomicilioUsuarioDeleteDialogComponent } from './domicilio-usuario-delete-dialog.component';
import { domicilioUsuarioRoute } from './domicilio-usuario.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(domicilioUsuarioRoute)],
  declarations: [
    DomicilioUsuarioComponent,
    DomicilioUsuarioDetailComponent,
    DomicilioUsuarioUpdateComponent,
    DomicilioUsuarioDeleteDialogComponent,
  ],
  entryComponents: [DomicilioUsuarioDeleteDialogComponent],
})
export class ResaMxWebDomicilioUsuarioModule {}
