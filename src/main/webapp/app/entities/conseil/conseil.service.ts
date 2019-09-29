import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConseil } from 'app/shared/model/conseil.model';

type EntityResponseType = HttpResponse<IConseil>;
type EntityArrayResponseType = HttpResponse<IConseil[]>;

@Injectable({ providedIn: 'root' })
export class ConseilService {
  public resourceUrl = SERVER_API_URL + 'api/conseils';

  constructor(protected http: HttpClient) {}

  create(conseil: IConseil): Observable<EntityResponseType> {
    return this.http.post<IConseil>(this.resourceUrl, conseil, { observe: 'response' });
  }

  update(conseil: IConseil): Observable<EntityResponseType> {
    return this.http.put<IConseil>(this.resourceUrl, conseil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConseil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConseil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
