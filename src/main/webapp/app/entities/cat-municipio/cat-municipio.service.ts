import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';

type EntityResponseType = HttpResponse<ICatMunicipio>;
type EntityArrayResponseType = HttpResponse<ICatMunicipio[]>;

@Injectable({ providedIn: 'root' })
export class CatMunicipioService {
  public resourceUrl = SERVER_API_URL + 'api/cat-municipios';

  constructor(protected http: HttpClient) {}

  create(catMunicipio: ICatMunicipio): Observable<EntityResponseType> {
    return this.http.post<ICatMunicipio>(this.resourceUrl, catMunicipio, { observe: 'response' });
  }

  update(catMunicipio: ICatMunicipio): Observable<EntityResponseType> {
    return this.http.put<ICatMunicipio>(this.resourceUrl, catMunicipio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatMunicipio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatMunicipio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
