import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRemede } from 'app/shared/model/remede.model';

type EntityResponseType = HttpResponse<IRemede>;
type EntityArrayResponseType = HttpResponse<IRemede[]>;

@Injectable({ providedIn: 'root' })
export class RemedeService {
  public resourceUrl = SERVER_API_URL + 'api/remedes';

  constructor(protected http: HttpClient) {}

  create(remede: IRemede): Observable<EntityResponseType> {
    return this.http.post<IRemede>(this.resourceUrl, remede, { observe: 'response' });
  }

  update(remede: IRemede): Observable<EntityResponseType> {
    return this.http.put<IRemede>(this.resourceUrl, remede, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemede>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemede[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
