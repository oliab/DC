import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';

type EntityResponseType = HttpResponse<ICatTipoOperacion>;
type EntityArrayResponseType = HttpResponse<ICatTipoOperacion[]>;

@Injectable({ providedIn: 'root' })
export class CatTipoOperacionService {
  public resourceUrl = SERVER_API_URL + 'api/cat-tipo-operacions';

  constructor(protected http: HttpClient) {}

  create(catTipoOperacion: ICatTipoOperacion): Observable<EntityResponseType> {
    return this.http.post<ICatTipoOperacion>(this.resourceUrl, catTipoOperacion, { observe: 'response' });
  }

  update(catTipoOperacion: ICatTipoOperacion): Observable<EntityResponseType> {
    return this.http.put<ICatTipoOperacion>(this.resourceUrl, catTipoOperacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatTipoOperacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatTipoOperacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
