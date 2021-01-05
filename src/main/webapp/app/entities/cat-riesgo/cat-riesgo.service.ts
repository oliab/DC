import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatRiesgo } from 'app/shared/model/cat-riesgo.model';

type EntityResponseType = HttpResponse<ICatRiesgo>;
type EntityArrayResponseType = HttpResponse<ICatRiesgo[]>;

@Injectable({ providedIn: 'root' })
export class CatRiesgoService {
  public resourceUrl = SERVER_API_URL + 'api/cat-riesgos';

  constructor(protected http: HttpClient) {}

  create(catRiesgo: ICatRiesgo): Observable<EntityResponseType> {
    return this.http.post<ICatRiesgo>(this.resourceUrl, catRiesgo, { observe: 'response' });
  }

  update(catRiesgo: ICatRiesgo): Observable<EntityResponseType> {
    return this.http.put<ICatRiesgo>(this.resourceUrl, catRiesgo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatRiesgo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatRiesgo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
