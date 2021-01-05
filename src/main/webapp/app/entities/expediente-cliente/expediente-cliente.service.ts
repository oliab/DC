import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpedienteCliente } from 'app/shared/model/expediente-cliente.model';

type EntityResponseType = HttpResponse<IExpedienteCliente>;
type EntityArrayResponseType = HttpResponse<IExpedienteCliente[]>;

@Injectable({ providedIn: 'root' })
export class ExpedienteClienteService {
  public resourceUrl = SERVER_API_URL + 'api/expediente-clientes';

  constructor(protected http: HttpClient) {}

  create(expedienteCliente: IExpedienteCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expedienteCliente);
    return this.http
      .post<IExpedienteCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expedienteCliente: IExpedienteCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expedienteCliente);
    return this.http
      .put<IExpedienteCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpedienteCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpedienteCliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expedienteCliente: IExpedienteCliente): IExpedienteCliente {
    const copy: IExpedienteCliente = Object.assign({}, expedienteCliente, {
      fechaAlta:
        expedienteCliente.fechaAlta && expedienteCliente.fechaAlta.isValid() ? expedienteCliente.fechaAlta.format(DATE_FORMAT) : undefined,
      fechaAct:
        expedienteCliente.fechaAct && expedienteCliente.fechaAct.isValid() ? expedienteCliente.fechaAct.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlta = res.body.fechaAlta ? moment(res.body.fechaAlta) : undefined;
      res.body.fechaAct = res.body.fechaAct ? moment(res.body.fechaAct) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((expedienteCliente: IExpedienteCliente) => {
        expedienteCliente.fechaAlta = expedienteCliente.fechaAlta ? moment(expedienteCliente.fechaAlta) : undefined;
        expedienteCliente.fechaAct = expedienteCliente.fechaAct ? moment(expedienteCliente.fechaAct) : undefined;
      });
    }
    return res;
  }
}
