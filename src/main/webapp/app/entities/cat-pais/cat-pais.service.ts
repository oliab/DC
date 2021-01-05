import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../shared/util/request-util';
import { ICatPais } from '../../shared/model/cat-pais.model';

type EntityResponseType = HttpResponse<ICatPais>;
type EntityArrayResponseType = HttpResponse<ICatPais[]>;

@Injectable({ providedIn: 'root' })
export class CatPaisService {
  public resourceUrl = SERVER_API_URL + 'api/cat-pais';

  constructor(protected http: HttpClient) {}

  create(catPais: ICatPais): Observable<EntityResponseType> {
    return this.http.post<ICatPais>(this.resourceUrl, catPais, { observe: 'response' });
  }

  update(catPais: ICatPais): Observable<EntityResponseType> {
    return this.http.put<ICatPais>(this.resourceUrl, catPais, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatPais>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatPais[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
