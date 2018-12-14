import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, AsyncSubject, BehaviorSubject } from 'rxjs';
import { Data } from '../interfaces/data';
import { map } from 'rxjs/operators';

import { PUBLIC_URL, ADMIN_URL } from '../url-settings'

import { cvSearchForm } from 'src/app/curriculum/curriculum-search/curriculum-search.component'
import { Employee, Curriculum } from '../interfaces/employee';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CurriculumService {

  constructor(private _http: HttpClient,
              private _router: Router) { }

  indType: Observable<any[]>
  indClass: Observable<any[]>
  employee: Employee
  userSource_: BehaviorSubject<any> = new BehaviorSubject<any>(this.employee);
  selectedUser$ = this.userSource_.asObservable()

  industryObj: IndustryType[]
  industryBase: IndustryType


  getPropertiesList(): Observable<any[]> {
    return this._http.get<any[]>(PUBLIC_URL + '/cvparams')
  }
  //temporary
  getBusinessLogic(): Observable<any[]>{
    return this._http.get<any[]>(PUBLIC_URL + '/industry-params')
  }

  searchShokumuRireki(params: cvSearchForm) {
    this._http.get<Employee[]>(ADMIN_URL + '/shokureki/search?'
      + 'id=' + params.id
      + '&n=' + params.name
      + '&k=' + params.kana
      + '&r=' + params.recruit
      + '&age=' + params.age
      + '&op=' + params.operator
      + '&exp=' + params.experience
      + '&idt=' + params.indType
      + '&db=' + params.dbms
      + '&os=' + params.os
      + '&lng=' + params.lang
      + '&tls=' + params.tools
      + '&res=' + params.response
      + '&mkr=' + params.maker
      + '&cm=' + params.customerName
      + '&tb=' + params.targetBusiness
      , {
        observe: 'response'
      }).subscribe(res =>{
        if (!res.body.length){
          alert('結果が見つかりません')
          return
      } else {
        this.userSource_.next(res.body)
        this._router.navigate(['/admin/shokumurirekisho/list'])
      }
      },
      err =>{
        console.log('ERROR, ERROR, ERROR')
        console.log(err)
      })
  }


}

export interface Gambiarra{
  tid: number,
  tdesc: string,
  cid: number,
  cdesc: string
}

export interface IndustryClass{  
  id: number
  desc: string
}

export interface IndustryType{
  id: number
  desc: string
  class: IndustryClass[]
}

