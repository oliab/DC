import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDomicilioEmpresa, DomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';
import { DomicilioEmpresaService } from './domicilio-empresa.service';
import { DomicilioEmpresaComponent } from './domicilio-empresa.component';
import { DomicilioEmpresaDetailComponent } from './domicilio-empresa-detail.component';
import { DomicilioEmpresaUpdateComponent } from './domicilio-empresa-update.component';

@Injectable({ providedIn: 'root' })
export class DomicilioEmpresaResolve implements Resolve<IDomicilioEmpresa> {
  constructor(private service: DomicilioEmpresaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDomicilioEmpresa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((domicilioEmpresa: HttpResponse<DomicilioEmpresa>) => {
          if (domicilioEmpresa.body) {
            return of(domicilioEmpresa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DomicilioEmpresa());
  }
}

export const domicilioEmpresaRoute: Routes = [
  {
    path: '',
    component: DomicilioEmpresaComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.domicilioEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DomicilioEmpresaDetailComponent,
    resolve: {
      domicilioEmpresa: DomicilioEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DomicilioEmpresaUpdateComponent,
    resolve: {
      domicilioEmpresa: DomicilioEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DomicilioEmpresaUpdateComponent,
    resolve: {
      domicilioEmpresa: DomicilioEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.domicilioEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
