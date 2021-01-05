import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatMoneda } from 'app/shared/model/cat-moneda.model';

type EntityResponseType = HttpResponse<ICatMoneda>;
type EntityArrayResponseType = HttpResponse<ICatMoneda[]>;

@Injectable({ providedIn: 'root' })
export class CatMonedaService {
  public resourceUrl = SERVER_API_URL + 'api/cat-monedas';

  constructor(protected http: HttpClient) {}

  create(catMoneda: ICatMoneda): Observable<EntityResponseType> {
    return this.http.post<ICatMoneda>(this.resourceUrl, catMoneda, { observe: 'response' });
  }

  update(catMoneda: ICatMoneda): Observable<EntityResponseType> {
    return this.http.put<ICatMoneda>(this.resourceUrl, catMoneda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatMoneda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatMoneda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
