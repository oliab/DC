import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatosUsuario } from 'app/shared/model/datos-usuario.model';

type EntityResponseType = HttpResponse<IDatosUsuario>;
type EntityArrayResponseType = HttpResponse<IDatosUsuario[]>;

@Injectable({ providedIn: 'root' })
export class DatosUsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/datos-usuarios';

  constructor(protected http: HttpClient) {}

  create(datosUsuario: IDatosUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datosUsuario);
    return this.http
      .post<IDatosUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(datosUsuario: IDatosUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datosUsuario);
    return this.http
      .put<IDatosUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDatosUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDatosUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(datosUsuario: IDatosUsuario): IDatosUsuario {
    const copy: IDatosUsuario = Object.assign({}, datosUsuario, {
      fechaAct: datosUsuario.fechaAct && datosUsuario.fechaAct.isValid() ? datosUsuario.fechaAct.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((datosUsuario: IDatosUsuario) => {
        datosUsuario.fechaAct = datosUsuario.fechaAct ? moment(datosUsuario.fechaAct) : undefined;
      });
    }
    return res;
  }
}
