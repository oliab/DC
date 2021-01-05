import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';

type EntityResponseType = HttpResponse<IDomicilioEmpresa>;
type EntityArrayResponseType = HttpResponse<IDomicilioEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class DomicilioEmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/domicilio-empresas';

  constructor(protected http: HttpClient) {}

  create(domicilioEmpresa: IDomicilioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilioEmpresa);
    return this.http
      .post<IDomicilioEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(domicilioEmpresa: IDomicilioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilioEmpresa);
    return this.http
      .put<IDomicilioEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDomicilioEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDomicilioEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(domicilioEmpresa: IDomicilioEmpresa): IDomicilioEmpresa {
    const copy: IDomicilioEmpresa = Object.assign({}, domicilioEmpresa, {
      fechaAct:
        domicilioEmpresa.fechaAct && domicilioEmpresa.fechaAct.isValid() ? domicilioEmpresa.fechaAct.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAct = res.body.fechaAct ? moment(res.body.fechaAct) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((domicilioEmpresa: IDomicilioEmpresa) => {
        domicilioEmpresa.fechaAct = domicilioEmpresa.fechaAct ? moment(domicilioEmpresa.fechaAct) : undefined;
      });
    }
    return res;
  }
}
