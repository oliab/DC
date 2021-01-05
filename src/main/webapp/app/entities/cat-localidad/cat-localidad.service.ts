import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';

type EntityResponseType = HttpResponse<ICatLocalidad>;
type EntityArrayResponseType = HttpResponse<ICatLocalidad[]>;

@Injectable({ providedIn: 'root' })
export class CatLocalidadService {
  public resourceUrl = SERVER_API_URL + 'api/cat-localidads';

  constructor(protected http: HttpClient) {}

  create(catLocalidad: ICatLocalidad): Observable<EntityResponseType> {
    return this.http.post<ICatLocalidad>(this.resourceUrl, catLocalidad, { observe: 'response' });
  }

  update(catLocalidad: ICatLocalidad): Observable<EntityResponseType> {
    return this.http.put<ICatLocalidad>(this.resourceUrl, catLocalidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatLocalidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatLocalidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
