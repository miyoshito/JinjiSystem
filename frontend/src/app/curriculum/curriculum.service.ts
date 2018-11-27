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

  indType: Observable<any[]>
  indClass: Observable<any[]>


  getPropertiesList(): Observable<any[]>{
    return this._http.get<any[]>(PUBLIC_URL+'/cv-params')
  }
  //temporary
  getBusinessLogic(): Observable<any[]>{
    return this._http.get<any[]>(PUBLIC_URL+'/industry-sublist') 
  }

  makeBusinessStructure(){
    this.getBusinessLogic().pipe(
      map((values) =>{
        console.log(values)
      })
    )
  }


}
