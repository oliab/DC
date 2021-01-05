import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from '../shared/constants/authority.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';
import { ResaMxWebSharedModule } from '../shared/shared.module';
import { ClientesComponent } from './clientes.component';
import { ClientesUpdateComponent } from './clientes-update.component';
import { ClientesDetailComponent } from './clientes-detail.component';
import { ClientesDetailGeneralesComponent } from './clientes-detail-generales.component';
import { ClientesDetailEmpresaComponent } from './clientes-detail-empresa.component';
import { ClientesDetailDireccionComponent } from './clientes-detail-direccion.component';
import { ClientesDatosGeneralesComponent } from './clientes-datos-generales.component';
import { ClientesDatosDireccionComponent } from './clientes-datos-direccion.component';
import { ClientesDatosEmpresaComponent } from './clientes-datos-empresa.component';
import { ClientesDeleteDialogComponent } from './clientes-delete-dialog.component';
import { ClientesResolve } from './clientes.resolve';

@NgModule({
  imports: [
    ResaMxWebSharedModule,
    RouterModule.forChild([
      {
        path: 'clientes',
        component: ClientesComponent,
        data: {
          defaultSort: 'id,asc',
          authorities: [Authority.SUPERVISOR],
          pageTitle: 'resaMxWebApp.catEstado.home.title',
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'clientes/new',
        component: ClientesUpdateComponent,
        data: {
          authorities: [Authority.SUPERVISOR],
          pageTitle: 'resaMxWebApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'clientes/:id/view',
        component: ClientesDetailComponent,
        resolve: {
          cliente: ClientesResolve,
        },
        data: {
          authorities: [Authority.SUPERVISOR],
          pageTitle: 'resaMxWebApp.cliente.home.title',
        },
        canActivate: [UserRouteAccessService],
      },
    ]),
  ],
  declarations: [
    ClientesComponent,
    ClientesUpdateComponent,
    ClientesDetailComponent,
    ClientesDetailGeneralesComponent,
    ClientesDetailDireccionComponent,
    ClientesDatosGeneralesComponent,
    ClientesDetailEmpresaComponent,
    ClientesDatosDireccionComponent,
    ClientesDatosEmpresaComponent,
    ClientesDeleteDialogComponent,
  ],
  entryComponents: [ClientesDeleteDialogComponent],
})
export class ResaMxWebClientesModule {}
