import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaladie } from 'app/shared/model/maladie.model';

type EntityResponseType = HttpResponse<IMaladie>;
type EntityArrayResponseType = HttpResponse<IMaladie[]>;

@Injectable({ providedIn: 'root' })
export class MaladieService {
  public resourceUrl = SERVER_API_URL + 'api/maladies';

  constructor(protected http: HttpClient) {}

  create(maladie: IMaladie): Observable<EntityResponseType> {
    return this.http.post<IMaladie>(this.resourceUrl, maladie, { observe: 'response' });
  }

  update(maladie: IMaladie): Observable<EntityResponseType> {
    return this.http.put<IMaladie>(this.resourceUrl, maladie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaladie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaladie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
