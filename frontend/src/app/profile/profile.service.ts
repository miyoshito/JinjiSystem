import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';

import { API_URL } from '../url-settings'
import { AuthService } from '../guards/auth.service';
import { Employee } from '../interfaces/employee';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  employee$: Employee

  _cacheUserSource: ReplaySubject<Employee> = new ReplaySubject<Employee>()
  cachedUser$ = this._cacheUserSource.asObservable()

  constructor(private _http: HttpClient,
              private _authService: AuthService) { }

  public getLoggedInUserData(): Observable<Employee>{
    console.log('getting...')
    return this._http.get<any>(API_URL+'/api/getmyinfos') //considerando que o HttpInterceptor vai mandar meu token pro sistema.
  }

  public cacheUser(){
    this.getLoggedInUserData().subscribe(data =>{
        console.log(data)
        this._cacheUserSource.next(data)
      })
  }

  public clearLoggedUser(){
    this._cacheUserSource.complete()//(new Employee(null,"","","","",null,"","",null,null,null,"","","","","","",null,null,null,null,"","",null,"",null))
  }
}
