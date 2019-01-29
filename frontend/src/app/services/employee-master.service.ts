import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpInterceptor, HttpParams } from '@angular/common/http';
import { Observable, Subject, ReplaySubject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { API_URL, PUBLIC_URL, ADMIN_URL } from 'src/app/url-settings'
import { catchError, map } from 'rxjs/operators';
import { TokenInterceptorService } from 'src/app/services/guards/token-interceptor.service';
import { Router } from '@angular/router';
import { userXAuth } from '../admin/system-settings/system-settings.component';


@Injectable({
  providedIn: 'root'
})
export class EmployeeMasterService {

  constructor(private _http: HttpClient,
              private interceptor: TokenInterceptorService,
              private _router: Router) { }

  //resultado da busca na tela de pesquisa => retorna um Array com os usuarios que batem com os params.
  private searchSource: ReplaySubject<Employee[]> = new ReplaySubject<Employee[]>(1)
  searchResults$ = this.searchSource.asObservable()

  //resultado da busca por ID, retorna o user em um Observavel que pode ser lido por qualquer tela.
  private usrSource: ReplaySubject<Employee> = new ReplaySubject<Employee>(1)
  employee$ = this.usrSource.asObservable()

  private usrAuthSettingsSource: ReplaySubject<userXAuth[]> = new ReplaySubject<userXAuth[]>()
  userAuthSettings$ = this.usrAuthSettingsSource.asObservable();

  
  getShainData(id: string, pa1?: string, pa2?: string, pa3?: string, pa4?: string){
    let param: HttpParams = new HttpParams()
    let params = new Array<string>(pa1, pa2, pa3, pa4)
    param = param.append("id", id)
    params.forEach(e =>{
      if (e != null && e != undefined) param = param.append(e,"true")
    })
    return this._http.get<Employee>(API_URL + '/se/data', {params: param, observe: 'response'}).pipe(
      map(res => {
        if (!res.body || res.body == null){
          return
        } else {
          this.usrSource.next(res.body)
          return
        }
      })
    ).subscribe()
  }

  getShainDatav2(id: string, pa1?: string, pa2?: string, pa3?: string, pa4?: string){
    let param: HttpParams = new HttpParams()
    let params = new Array<string>(pa1, pa2, pa3, pa4)
    param = param.append("id", id)
    params.forEach(e =>{
      if (e != null && e != undefined) param = param.append(e,"true")
    })
    return this._http.get<Employee>(API_URL + '/se/data', {params: param, observe: 'response'})
  }

  
  checkIfShainExists(id: string): Observable<boolean>{
  let d: Subject<boolean> = new Subject<boolean>()
    this._http.get<Employee>(API_URL +'/se/data/'+id, {observe: 'response'}).pipe(
      map(res =>{
        if (!res.body || res.body == null){
          d.next(false)
        } else {
          this.usrSource.next(res.body)
          d.next(true)
        }
      })
    ).subscribe()       
    return d
  }

  //carrega os dados do banco pra criar a view da pagina
  getViewRendering(): Observable<any>{
      return this._http.get<any>(PUBLIC_URL+'/employee-params')
  }

  insertShainAttempt(employee: Employee){
    return this._http.post<Employee>(ADMIN_URL+'/add-employee', employee, {
      observe: 'response'})
      .subscribe(
    resp => {},
    err => {
      alert("登録ときに問題が見つかりました。管理者に連絡してください。")
      throw err
    }
  )}

  getAllUsers(){
    return this._http.get<Employee[]>(ADMIN_URL + '/getallemployees', {observe: 'response'})
  }

  searchShain(map: Map<string, string>){

    let httpParams: HttpParams = new HttpParams()

    map.forEach((k,v) =>{
      httpParams = httpParams.append(v,k)
    })

  
    return this._http.get<Employee[]>(ADMIN_URL + '/search-employee',
    {params: httpParams, observe: 'response'}).subscribe(res =>{

      if (res.body == null){
        alert('データーが見つかりません。')  
        return
      }
      this._router.navigate(['/admin/employee-list'])
      this.searchSource.next(res.body)
    })
  }

  saveUserAuthoritiesChanges(users: any[]){
    return this._http.put<any[]>(API_URL + '/auth/changeuserpermissions',users, {observe: 'response'})
    .pipe(map(res =>{
      if (res.status == 200){
        alert('変更しました')
        this._router.navigate(['/home'])
        return
      }
    })).subscribe()
  }

  getUserAuthorities(id?: string){
     let p: HttpParams = new HttpParams()
      if(id) p.append("id", id)
    return this._http.get<userXAuth[]>(API_URL + '/auth/loaduserpermissions',{params: p, observe: 'response'}).pipe(
      map(m =>{
        if (m.body.length == 0){
          alert('nothing found')
          return;
        }
        else this.usrAuthSettingsSource.next(m.body)
      })
    ).subscribe()
  }


}
