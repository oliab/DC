import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { ExpedienteClienteComponent } from './expediente-cliente.component';
import { ExpedienteClienteDetailComponent } from './expediente-cliente-detail.component';
import { ExpedienteClienteUpdateComponent } from './expediente-cliente-update.component';
import { ExpedienteClienteDeleteDialogComponent } from './expediente-cliente-delete-dialog.component';
import { expedienteClienteRoute } from './expediente-cliente.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(expedienteClienteRoute)],
  declarations: [
    ExpedienteClienteComponent,
    ExpedienteClienteDetailComponent,
    ExpedienteClienteUpdateComponent,
    ExpedienteClienteDeleteDialogComponent,
  ],
  entryComponents: [ExpedienteClienteDeleteDialogComponent],
})
export class ResaMxWebExpedienteClienteModule {}
