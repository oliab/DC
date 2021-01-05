import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDomicilioUsuario, DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { DomicilioUsuarioService } from './domicilio-usuario.service';
import { DomicilioUsuarioComponent } from './domicilio-usuario.component';
import { DomicilioUsuarioDetailComponent } from './domicilio-usuario-detail.component';
import { DomicilioUsuarioUpdateComponent } from './domicilio-usuario-update.component';

@Injectable({ providedIn: 'root' })
export class DomicilioUsuarioResolve implements Resolve<IDomicilioUsuario> {
  constructor(private service: DomicilioUsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDomicilioUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((domicilioUsuario: HttpResponse<DomicilioUsuario>) => {
          if (domicilioUsuario.body) {
            return of(domicilioUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DomicilioUsuario());
  }
}

export const domicilioUsuarioRoute: Routes = [
  {
    path: '',
    component: DomicilioUsuarioComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.domicilioUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DomicilioUsuarioDetailComponent,
    resolve: {
      domicilioUsuario: DomicilioUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DomicilioUsuarioUpdateComponent,
    resolve: {
      domicilioUsuario: DomicilioUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DomicilioUsuarioUpdateComponent,
    resolve: {
      domicilioUsuario: DomicilioUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
