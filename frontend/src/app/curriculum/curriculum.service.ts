import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '../interfaces/data';
import { map } from 'rxjs/operators';

import { PUBLIC_URL } from '../url-settings'

@Injectable({
  providedIn: 'root'
})
export class CurriculumService {  

  constructor(private _http: HttpClient) { }


  getPropertiesList(): Observable<any[]>{
    return this._http.get<any[]>(PUBLIC_URL+'/cv-params')
  }


}
