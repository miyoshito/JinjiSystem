import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { UrlSettings } from '../../url-settings'
const url = 'http://192.168.10.120:8080'
@Injectable({
  providedIn: 'root'
})
export class EmployeeMasterService {

  constructor(private _http: HttpClient) { }

  
  emParam(): Observable<any>{
      return this._http.get<any>(url+'/api/employee-dependencies',{
        headers: new HttpHeaders(
          { 'Content-Type': 'application/json; charset=utf-8','Authorization':localStorage.getItem('currentUser')})
    })
  }

  showalert(){
    alert('In development...')
  }

  public insertShainAttempt(employee: Employee){
    console.log('object sended to spring:')
    console.log(employee)
    return this._http.post<any>(url+'/api/add-employee', employee, {
      headers: {'Content-Type': 'application/json; charset=utf-8',
                'Authorization': localStorage.getItem('Authorization')}
    })    
  }

}
