import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '../interfaces/data';
import { map } from 'rxjs/operators';

import { API_URL } from '../url-settings'

const url = API_URL

@Injectable({
  providedIn: 'root'
})
export class CurriculumService {


  

  constructor(private _http: HttpClient) { }


  getPropertiesList(): Observable<Data[]>{
    return this._http.get<Data[]>(url+'/api/getall')
  }


}
