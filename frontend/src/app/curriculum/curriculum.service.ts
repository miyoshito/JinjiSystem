import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, AsyncSubject, BehaviorSubject } from 'rxjs';
import { Data, cvForm, SkillMap } from '../interfaces/data';
import { map } from 'rxjs/operators';

import { PUBLIC_URL, ADMIN_URL } from '../url-settings'
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

  getPropertiesList(): Observable<any[]> {
    return this._http.get<any[]>(PUBLIC_URL + '/cvparams')
  }
  //temporary
  getBusinessLogic(): Observable<any[]> {
    return this._http.get<any[]>(PUBLIC_URL + '/industry-params')
  }

  insertShokumuAttempt(cv: any) {
    return this._http.post<any>(ADMIN_URL + '/shokureki/add', cv, { observe: 'response' }).subscribe()
  }

  deleteShokumuAttempt(sid: number) {
    return this._http.put<any>(ADMIN_URL + '/shokureki/delete?sid=' + sid, null, { observe: 'response' }).subscribe()
  }

  searchShokumuRireki(params: cvForm) {
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
      }).subscribe(res => {
        if (!res.body.length) {
          alert('結果が見つかりません')
          return
        } else {
          this.userSource_.next(res.body)
          this._router.navigate(['/admin/shokumurirekisho/list'])
        }
      },
        err => {
          console.log('ERROR, ERROR, ERROR')
          console.log(err)
        })
  }
}