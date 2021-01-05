import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';

type EntityResponseType = HttpResponse<ICatSucursal>;
type EntityArrayResponseType = HttpResponse<ICatSucursal[]>;

@Injectable({ providedIn: 'root' })
export class CatSucursalService {
  public resourceUrl = SERVER_API_URL + 'api/cat-sucursals';

  constructor(protected http: HttpClient) {}

  create(catSucursal: ICatSucursal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catSucursal);
    return this.http
      .post<ICatSucursal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(catSucursal: ICatSucursal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catSucursal);
    return this.http
      .put<ICatSucursal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICatSucursal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICatSucursal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(catSucursal: ICatSucursal): ICatSucursal {
    const copy: ICatSucursal = Object.assign({}, catSucursal, {
      fechaAct: catSucursal.fechaAct && catSucursal.fechaAct.isValid() ? catSucursal.fechaAct.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((catSucursal: ICatSucursal) => {
        catSucursal.fechaAct = catSucursal.fechaAct ? moment(catSucursal.fechaAct) : undefined;
      });
    }
    return res;
  }
}
