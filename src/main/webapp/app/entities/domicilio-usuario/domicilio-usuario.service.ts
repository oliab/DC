import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

type EntityResponseType = HttpResponse<IDomicilioUsuario>;
type EntityArrayResponseType = HttpResponse<IDomicilioUsuario[]>;

@Injectable({ providedIn: 'root' })
export class DomicilioUsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/domicilio-usuarios';

  constructor(protected http: HttpClient) {}

  create(domicilioUsuario: IDomicilioUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilioUsuario);
    return this.http
      .post<IDomicilioUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(domicilioUsuario: IDomicilioUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilioUsuario);
    return this.http
      .put<IDomicilioUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDomicilioUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDomicilioUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(domicilioUsuario: IDomicilioUsuario): IDomicilioUsuario {
    const copy: IDomicilioUsuario = Object.assign({}, domicilioUsuario, {
      fechaAct:
        domicilioUsuario.fechaAct && domicilioUsuario.fechaAct.isValid() ? domicilioUsuario.fechaAct.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((domicilioUsuario: IDomicilioUsuario) => {
        domicilioUsuario.fechaAct = domicilioUsuario.fechaAct ? moment(domicilioUsuario.fechaAct) : undefined;
      });
    }
    return res;
  }
}
