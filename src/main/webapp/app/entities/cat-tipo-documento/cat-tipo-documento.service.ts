import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';

type EntityResponseType = HttpResponse<ICatTipoDocumento>;
type EntityArrayResponseType = HttpResponse<ICatTipoDocumento[]>;

@Injectable({ providedIn: 'root' })
export class CatTipoDocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/cat-tipo-documentos';

  constructor(protected http: HttpClient) {}

  create(catTipoDocumento: ICatTipoDocumento): Observable<EntityResponseType> {
    return this.http.post<ICatTipoDocumento>(this.resourceUrl, catTipoDocumento, { observe: 'response' });
  }

  update(catTipoDocumento: ICatTipoDocumento): Observable<EntityResponseType> {
    return this.http.put<ICatTipoDocumento>(this.resourceUrl, catTipoDocumento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatTipoDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatTipoDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
