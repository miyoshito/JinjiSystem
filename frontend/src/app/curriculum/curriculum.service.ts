import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '../interfaces/data';
import { map } from 'rxjs/operators';

const url = 'http://192.168.10.120:8080'

@Injectable({
  providedIn: 'root'
})
export class CurriculumService {


  

  constructor(private _http: HttpClient) { }


  getPropertiesList(): Observable<Data[]>{
    return this._http.get<Data[]>(url+'/api/getall')
  }


}
