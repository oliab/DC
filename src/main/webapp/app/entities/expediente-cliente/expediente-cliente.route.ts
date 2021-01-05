import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpedienteCliente, ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';
import { ExpedienteClienteService } from './expediente-cliente.service';
import { ExpedienteClienteComponent } from './expediente-cliente.component';
import { ExpedienteClienteDetailComponent } from './expediente-cliente-detail.component';
import { ExpedienteClienteUpdateComponent } from './expediente-cliente-update.component';

@Injectable({ providedIn: 'root' })
export class ExpedienteClienteResolve implements Resolve<IExpedienteCliente> {
  constructor(private service: ExpedienteClienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpedienteCliente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expedienteCliente: HttpResponse<ExpedienteCliente>) => {
          if (expedienteCliente.body) {
            return of(expedienteCliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExpedienteCliente());
  }
}

export const expedienteClienteRoute: Routes = [
  {
    path: '',
    component: ExpedienteClienteComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.expedienteCliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpedienteClienteDetailComponent,
    resolve: {
      expedienteCliente: ExpedienteClienteResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.expedienteCliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpedienteClienteUpdateComponent,
    resolve: {
      expedienteCliente: ExpedienteClienteResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.expedienteCliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpedienteClienteUpdateComponent,
    resolve: {
      expedienteCliente: ExpedienteClienteResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.expedienteCliente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
