import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, AsyncSubject, BehaviorSubject, ReplaySubject } from 'rxjs';
import { Data, cvForm, SkillMap } from 'src/app/interfaces/data';
import { map } from 'rxjs/operators';

import { PUBLIC_URL, ADMIN_URL, API_URL } from 'src/app/url-settings'
import { Employee, Curriculum } from 'src/app/interfaces/employee';
import { Router } from '@angular/router';
import { SkillMapData } from 'src/app/interfaces/skillmap';

@Injectable({
  providedIn: 'root'
})
export class CurriculumService {

  constructor(private _http: HttpClient,
    private _router: Router) { }

  indType: Observable<any[]>
  indClass: Observable<any[]>
  employee: Employee
  skillmap: SkillMapData

  private userSource_: BehaviorSubject<any> = new BehaviorSubject<any>(this.employee);
  selectedUser$ = this.userSource_.asObservable()

  //get necessary information for building the insert and search screens.
  getSearchableParams(){
    return this._http.get<any[]>(PUBLIC_URL + '/shokurirekisearchlist')
  }
  getPropertiesList(){
    return this._http.get<any[]>(PUBLIC_URL + '/cvparams')
  }
  getBusinessLogic(): Observable<any[]> {
    return this._http.get<any[]>(PUBLIC_URL + '/industry-params')
  }
  //insert attempt -> validation is made on component.
  insertShokumuAttempt(cv: any) {
    return this._http.post<any>(API_URL + '/se/shokureki/add', cv, { observe: 'response' }).subscribe()
  }
  //delete attempt DO NOT remove data, just set to inactive.
  deleteShokumuAttempt(sid: number) {
    return this._http.put<any>(ADMIN_URL + '/shokureki/delete?sid=' + sid, null, { observe: 'response' }).subscribe()
  }
  //shokumurireki/search function
  searchShokumuRireki(map: Map<string, string>) {    
    let par: HttpParams = new HttpParams();
    map.forEach((k,v) =>{
      par = par.append(v,k)
    })
    this._http.get<Employee[]>(API_URL + '/se/shokureki/search', {observe: 'response', params: par})
    .subscribe(res => {
        if (!res.body || res.body.length == 0) {
          alert('結果が見つかりません')
          return
        } else {
          this.userSource_.next(res.body)
          this._router.navigate(['/public/shokumurirekisho/list'])
        }
    })
  }
}