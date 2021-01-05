import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

type EntityResponseType = HttpResponse<ICatTipoMonetario>;
type EntityArrayResponseType = HttpResponse<ICatTipoMonetario[]>;

@Injectable({ providedIn: 'root' })
export class CatTipoMonetarioService {
  public resourceUrl = SERVER_API_URL + 'api/cat-tipo-monetarios';

  constructor(protected http: HttpClient) {}

  create(catTipoMonetario: ICatTipoMonetario): Observable<EntityResponseType> {
    return this.http.post<ICatTipoMonetario>(this.resourceUrl, catTipoMonetario, { observe: 'response' });
  }

  update(catTipoMonetario: ICatTipoMonetario): Observable<EntityResponseType> {
    return this.http.put<ICatTipoMonetario>(this.resourceUrl, catTipoMonetario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatTipoMonetario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatTipoMonetario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
