import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { API_URL } from '../url-settings';
import { Employee } from '../interfaces/employee';
import { ReplaySubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QualificationsService {

    
  private _searchResultsSource: ReplaySubject<Employee[]> = new ReplaySubject<Employee[]>(1)
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
    
    return this._httpClient.get<Employee[]>(API_URL + '/public/qualifications/search', {params: params, observe: 'response'})
  }

  pushSearchResults(emp: Employee[]){
    this._searchResultsSource.next(emp)
  }
}
