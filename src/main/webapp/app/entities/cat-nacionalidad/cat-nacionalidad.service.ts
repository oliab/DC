import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';

type EntityResponseType = HttpResponse<ICatNacionalidad>;
type EntityArrayResponseType = HttpResponse<ICatNacionalidad[]>;

@Injectable({ providedIn: 'root' })
export class CatNacionalidadService {
  public resourceUrl = SERVER_API_URL + 'api/cat-nacionalidads';

  constructor(protected http: HttpClient) {}

  create(catNacionalidad: ICatNacionalidad): Observable<EntityResponseType> {
    return this.http.post<ICatNacionalidad>(this.resourceUrl, catNacionalidad, { observe: 'response' });
  }

  update(catNacionalidad: ICatNacionalidad): Observable<EntityResponseType> {
    return this.http.put<ICatNacionalidad>(this.resourceUrl, catNacionalidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatNacionalidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatNacionalidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
