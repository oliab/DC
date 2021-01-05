import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';

type EntityResponseType = HttpResponse<ICatIdentificacion>;
type EntityArrayResponseType = HttpResponse<ICatIdentificacion[]>;

@Injectable({ providedIn: 'root' })
export class CatIdentificacionService {
  public resourceUrl = SERVER_API_URL + 'api/cat-identificacions';

  constructor(protected http: HttpClient) {}

  create(catIdentificacion: ICatIdentificacion): Observable<EntityResponseType> {
    return this.http.post<ICatIdentificacion>(this.resourceUrl, catIdentificacion, { observe: 'response' });
  }

  update(catIdentificacion: ICatIdentificacion): Observable<EntityResponseType> {
    return this.http.put<ICatIdentificacion>(this.resourceUrl, catIdentificacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatIdentificacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatIdentificacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
