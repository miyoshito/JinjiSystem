import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../url-settings';

@Injectable({
  providedIn: 'root'
})
export class QualificationsService {

  constructor(private _httpClient: HttpClient) { }

  getShikakuSearchParams(){
    return this._httpClient.get<any>(API_URL + '/public/shikakusearchlist')
  }
}
