import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { DatosUsuarioComponent } from './datos-usuario.component';
import { DatosUsuarioDetailComponent } from './datos-usuario-detail.component';
import { DatosUsuarioUpdateComponent } from './datos-usuario-update.component';
import { DatosUsuarioDeleteDialogComponent } from './datos-usuario-delete-dialog.component';
import { datosUsuarioRoute } from './datos-usuario.route';

@NgModule({
  imports: [ResaMxWebSharedModule, RouterModule.forChild(datosUsuarioRoute)],
  declarations: [DatosUsuarioComponent, DatosUsuarioDetailComponent, DatosUsuarioUpdateComponent, DatosUsuarioDeleteDialogComponent],
  entryComponents: [DatosUsuarioDeleteDialogComponent],
})
export class ResaMxWebDatosUsuarioModule {}
