import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatCP } from 'app/shared/model/cat-cp.model';

type EntityResponseType = HttpResponse<ICatCP>;
type EntityArrayResponseType = HttpResponse<ICatCP[]>;

@Injectable({ providedIn: 'root' })
export class CatCPService {
  public resourceUrl = SERVER_API_URL + 'api/cat-cps';

  constructor(protected http: HttpClient) {}

  create(catCP: ICatCP): Observable<EntityResponseType> {
    return this.http.post<ICatCP>(this.resourceUrl, catCP, { observe: 'response' });
  }

  update(catCP: ICatCP): Observable<EntityResponseType> {
    return this.http.put<ICatCP>(this.resourceUrl, catCP, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatCP>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatCP[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
