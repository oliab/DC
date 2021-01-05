import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';

type EntityResponseType = HttpResponse<ICatTipoEmpresa>;
type EntityArrayResponseType = HttpResponse<ICatTipoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class CatTipoEmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/cat-tipo-empresas';

  constructor(protected http: HttpClient) {}

  create(catTipoEmpresa: ICatTipoEmpresa): Observable<EntityResponseType> {
    return this.http.post<ICatTipoEmpresa>(this.resourceUrl, catTipoEmpresa, { observe: 'response' });
  }

  update(catTipoEmpresa: ICatTipoEmpresa): Observable<EntityResponseType> {
    return this.http.put<ICatTipoEmpresa>(this.resourceUrl, catTipoEmpresa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatTipoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatTipoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
