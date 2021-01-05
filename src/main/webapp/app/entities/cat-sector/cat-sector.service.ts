import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatSector } from 'app/shared/model/cat-sector.model';

type EntityResponseType = HttpResponse<ICatSector>;
type EntityArrayResponseType = HttpResponse<ICatSector[]>;

@Injectable({ providedIn: 'root' })
export class CatSectorService {
  public resourceUrl = SERVER_API_URL + 'api/cat-sectors';

  constructor(protected http: HttpClient) {}

  create(catSector: ICatSector): Observable<EntityResponseType> {
    return this.http.post<ICatSector>(this.resourceUrl, catSector, { observe: 'response' });
  }

  update(catSector: ICatSector): Observable<EntityResponseType> {
    return this.http.put<ICatSector>(this.resourceUrl, catSector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatSector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatSector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
