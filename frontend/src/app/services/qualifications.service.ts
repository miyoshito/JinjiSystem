import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { API_URL } from '../url-settings';
import { Employee } from '../interfaces/employee';
import { ReplaySubject } from 'rxjs';
import { Qualifications } from '../interfaces/qualifications';

@Injectable({
  providedIn: 'root'
})
export class QualificationsService {

    
  private _searchResultsSource: ReplaySubject<Qualifications[]> = new ReplaySubject<Qualifications[]>(1)
  searchResults$ = this._searchResultsSource.asObservable();

  constructor(private _httpClient: HttpClient) { }

  getShikakuSearchParams(){
    return this._httpClient.get<any>(API_URL + '/public/shikakusearchlist')
  }

  searchForShikaku(map: Map<string, string>){
    let params: HttpParams = new HttpParams()
    map.forEach((k,v) =>{
      params = params.append(v,k)
    })

    return this._httpClient.get<Qualifications[]>(API_URL + '/se/qualifications/search', {params: params, observe: 'response'})
  }

  pushSearchResults(emp: Qualifications[]){
    this._searchResultsSource.next(emp)
  }

  insertAttempt(ql: Qualifications){
    return this._httpClient.post<Qualifications>(API_URL+'/se/qualifications/add',ql,{observe: 'response'})
  }
}
