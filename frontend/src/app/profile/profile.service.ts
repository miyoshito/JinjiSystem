import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';

import { API_URL, ADMIN_URL } from '../url-settings'
import { AuthService } from '../guards/auth.service';
import { Employee } from '../interfaces/employee';
import { map } from 'rxjs/operators';
import { BroadcastService } from '../broadcast.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  employee$: Employee

  _cacheUserSource: ReplaySubject<Employee> = new ReplaySubject<Employee>()
  cachedUser$ = this._cacheUserSource.asObservable()

  constructor(private _http: HttpClient,
              private _authService: AuthService,
              private _broadcastService: BroadcastService) { }

  public getLoggedInUserData(): Observable<Employee>{
    return this._http.get<any>(API_URL+'/api/se/getmyinfos') //considerando que o HttpInterceptor vai mandar meu token pro sistema.
  }

  public getUserProfile(id: string): Observable<Employee>{
    return this._http.get<any>(ADMIN_URL+'/getprofile/'+id)
  }

  public cacheUser(){
    this.getLoggedInUserData().subscribe(data =>{
        this._broadcastService.pushAuthorization(data.role.roledesc)
        this._cacheUserSource.next(data)
      })
  }

  public clearLoggedUser(){
    this._cacheUserSource.complete()//(new Employee(null,"","","","",null,"","",null,null,null,"","","","","","",null,null,null,null,"","",null,"",null))
  }
}
