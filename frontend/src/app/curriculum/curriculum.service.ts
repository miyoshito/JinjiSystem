import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, AsyncSubject, BehaviorSubject, ReplaySubject } from 'rxjs';
import { Data, cvForm, SkillMap } from '../interfaces/data';
import { map } from 'rxjs/operators';

import { PUBLIC_URL, ADMIN_URL, API_URL } from '../url-settings'
import { Employee, Curriculum } from '../interfaces/employee';
import { Router } from '@angular/router';
import { SkillMapData } from '../interfaces/skillmap';

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

  private buildMapSource_: ReplaySubject<any> = new ReplaySubject<any[]>(0)
  SkillMapSearchResults$ = this.buildMapSource_.asObservable()



  getPropertiesList(){
    return this._http.get<any[]>(PUBLIC_URL + '/cvparams')
  }

  getBusinessLogic(): Observable<any[]> {
    return this._http.get<any[]>(PUBLIC_URL + '/industry-params')
  }

  insertShokumuAttempt(cv: any) {
    return this._http.post<any>(ADMIN_URL + '/shokureki/add', cv, { observe: 'response' }).subscribe()
  }

  deleteShokumuAttempt(sid: number) {
    return this._http.put<any>(ADMIN_URL + '/shokureki/delete?sid=' + sid, null, { observe: 'response' }).subscribe()
  }

  getSkillMaps(id?: string, name?: string, sh?:string, kt?:string){

    (!id) ? id = '' : id;
    (!name) ? name = '' : name;
    (!sh) ? sh = '' : sh;
    (!kt) ? kt = '' : kt;

    this._http.get<SkillMapData[]>(API_URL + '/api/se/skillmapbuilder?'
    + 'id=' + id
    + '&nm=' + name
    + '&sh=' + sh
    + '&kt=' + kt
    , {
      observe: 'response'
    }).subscribe(res =>{
      if(!res.body.length){
        alert('結果が見つかりません')
        return
      } else this.buildMapSource_.next(res.body)
    })
  }

  getAllShokumuRireki(){
    return this._http.get<Employee[]>(ADMIN_URL + '/shokureki/getall', {observe: 'response'}).pipe(
      map(rireki => {
        if (!rireki.body.length) {
          alert('結果が見つかりません')
          return
        } else {
        this.userSource_.next(rireki.body)
        this._router.navigate(['/admin/shokumurirekisho/list'])
        }
      })
    ).subscribe()
  }

  searchShokumuRireki(map: Map<string, string>) {
    
    let par: HttpParams = new HttpParams();

    map.forEach((k,v) =>{
      par = par.append(v,k)
    })

    

    
    this._http.get<Employee[]>(ADMIN_URL + '/shokureki/search', {observe: 'response', params: par})
    .subscribe(res => {
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