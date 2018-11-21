import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { API_URL, PUBLIC_URL, ADMIN_URL } from '../../url-settings'
import { catchError } from 'rxjs/operators';
import { TokenInterceptorService } from 'src/app/guards/token-interceptor.service';

const url = API_URL
@Injectable({
  providedIn: 'root'
})
export class EmployeeMasterService {

  constructor(private _http: HttpClient,
              private interceptor: TokenInterceptorService) { }

  getEmployeeList(): Observable<Employee[]>{
    return this._http.get<Employee[]>(ADMIN_URL+'/employee-list')
  }  
  
  getViewRendering(): Observable<any>{
      return this._http.get<any>(PUBLIC_URL+'/employee-dependencies')
  }

  getAffiliations(): Observable<any>{
      return this._http.get<any>(PUBLIC_URL+'/shozoku-list')
  }

  public insertShainAttempt(employee: Employee){
    return this._http.post<any>(ADMIN_URL+'/add-employee', employee, {
      observe: 'response'})
      .subscribe(resp => {
        if (resp.status === 201)
        alert("Employee Inserted")
    },
    err => {
      alert("Something bad happened !")
      throw err
    }
  )}
}
