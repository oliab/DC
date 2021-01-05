import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ClienteDTO, ICliente } from 'app/shared/model/cliente.model';

type EntityResponseType = HttpResponse<ICliente>;
type EntityArrayResponseType = HttpResponse<ICliente[]>;

@Injectable({ providedIn: 'root' })
export class ClientesService {
  public resourceUrl = SERVER_API_URL + 'api/clientes';

  constructor(protected http: HttpClient) {}

  create(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .post<ICliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  nuevo(cliente: ClienteDTO): Observable<EntityResponseType> {
    return this.http
      .post<ICliente>(this.resourceUrl + '/new', cliente, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .put<ICliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findDTO(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICliente>(`${this.resourceUrl}/view/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cliente: ICliente): ICliente {
    const copy: ICliente = Object.assign({}, cliente, {
      fechaAlta: cliente.fechaAlta && cliente.fechaAlta.isValid() ? cliente.fechaAlta.format(DATE_FORMAT) : undefined,
      fechaAct: cliente.fechaAct && cliente.fechaAct.isValid() ? cliente.fechaAct.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((cliente: ICliente) => {
        cliente.fechaAlta = cliente.fechaAlta ? moment(cliente.fechaAlta) : undefined;
        cliente.fechaAct = cliente.fechaAct ? moment(cliente.fechaAct) : undefined;
      });
    }
    return res;
  }
}
