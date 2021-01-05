import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDatosUsuario, DatosUsuario } from 'app/shared/model/datos-usuario.model';
import { DatosUsuarioService } from './datos-usuario.service';
import { DatosUsuarioComponent } from './datos-usuario.component';
import { DatosUsuarioDetailComponent } from './datos-usuario-detail.component';
import { DatosUsuarioUpdateComponent } from './datos-usuario-update.component';

@Injectable({ providedIn: 'root' })
export class DatosUsuarioResolve implements Resolve<IDatosUsuario> {
  constructor(private service: DatosUsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDatosUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((datosUsuario: HttpResponse<DatosUsuario>) => {
          if (datosUsuario.body) {
            return of(datosUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DatosUsuario());
  }
}

export const datosUsuarioRoute: Routes = [
  {
    path: '',
    component: DatosUsuarioComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.datosUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatosUsuarioDetailComponent,
    resolve: {
      datosUsuario: DatosUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.datosUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatosUsuarioUpdateComponent,
    resolve: {
      datosUsuario: DatosUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.datosUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatosUsuarioUpdateComponent,
    resolve: {
      datosUsuario: DatosUsuarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.datosUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
