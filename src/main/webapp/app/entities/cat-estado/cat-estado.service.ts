import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatEstado } from 'app/shared/model/cat-estado.model';

type EntityResponseType = HttpResponse<ICatEstado>;
type EntityArrayResponseType = HttpResponse<ICatEstado[]>;

@Injectable({ providedIn: 'root' })
export class CatEstadoService {
  public resourceUrl = SERVER_API_URL + 'api/cat-estados';

  constructor(protected http: HttpClient) {}

  create(catEstado: ICatEstado): Observable<EntityResponseType> {
    return this.http.post<ICatEstado>(this.resourceUrl, catEstado, { observe: 'response' });
  }

  update(catEstado: ICatEstado): Observable<EntityResponseType> {
    return this.http.put<ICatEstado>(this.resourceUrl, catEstado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatEstado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatEstado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
