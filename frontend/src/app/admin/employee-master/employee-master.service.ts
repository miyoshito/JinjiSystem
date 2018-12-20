import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpInterceptor } from '@angular/common/http';
import { Observable, Subject, ReplaySubject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { API_URL, PUBLIC_URL, ADMIN_URL } from '../../url-settings'
import { catchError } from 'rxjs/operators';
import { TokenInterceptorService } from 'src/app/guards/token-interceptor.service';
import { Router } from '@angular/router';

const url = API_URL
@Injectable({
  providedIn: 'root'
})
export class EmployeeMasterService {

  constructor(private _http: HttpClient,
              private interceptor: TokenInterceptorService,
              private _router: Router) { }

  searchSource: ReplaySubject<Employee[]> = new ReplaySubject<Employee[]>()
  searchResults$ = this.searchSource.asObservable()  


  
  getViewRendering(): Observable<any>{
      return this._http.get<any>(PUBLIC_URL+'/employee-params')
  }

  insertShainAttempt(employee: Employee){
    return this._http.post<Employee>(ADMIN_URL+'/add-employee', employee, {
      observe: 'response'})
      .subscribe(
    resp => {},
    err => {
      alert("Something bad happened ! You gonna be disconnected for security purposes.")
      throw err
    }
  )}

  getAllShains(){
    return this._http.get<Employee[]>(ADMIN_URL+'/employee-list', {observe: 'response'}).subscribe(
      res => {
        this.searchSource.next(res.body)
      }
    )
  }  

  searchShain(id: string, name: string, kana: string, aff: number[]){
    let affs = ''
    if (aff.length > 0) {
     affs = aff.map(s => s).join(',')
    console.log(affs)
    }
    return this._http.get<Employee[]>(ADMIN_URL+
    '/search-employee?id='+id
    +'&name='+name
    +'&kana='+kana
    +'&affiliation='+affs,
    {observe: 'response'}).subscribe(res =>{
      console.log(res.body)
      this._router.navigate(['/admin/employee-list'])
      this.searchSource.next(res.body)
    })
  }


}
