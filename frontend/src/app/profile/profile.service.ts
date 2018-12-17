import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, Subject, Subscription, AsyncSubject } from 'rxjs';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';

import { API_URL, ADMIN_URL } from '../url-settings'
import { AuthService } from '../guards/auth.service';
import { Employee } from '../interfaces/employee';
import { map, tap, takeUntil } from 'rxjs/operators';
import { BroadcastService } from '../broadcast.service';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  employee$: Observable<Employee>
  isAuthenticated$: Subject<any>

  employee: Employee// = new Employee(null,"","","","",null,"","",null,null,null,"","","","","","",null,null,null,null,"","",null,"",null,null)

  private _cacheUserSource: ReplaySubject<Employee> = new ReplaySubject<Employee>(1)
  cachedUser$ = this._cacheUserSource.asObservable()

  constructor(private _http: HttpClient,
              private _broadcastService: BroadcastService,
              private _loginService: LoginService
              ) { }

  public getLoggedInUserData(){
    return this._http.get<Employee>(API_URL+'/api/se/getmyinfos',
    {observe: 'response'}).subscribe(data =>{
    this._cacheUserSource.next(data.body)
    this._broadcastService.pushAuthentication(true)
    this._broadcastService.pushAuthorization(data.body.role.roledesc)
    },
    err =>{    
      this._loginService.logout()
      console.log(err)
    })
    //considerando que o HttpInterceptor vai mandar meu token pro sistema.
  }

  public getUserProfile(id: string){
    return this._http.get<Employee>(ADMIN_URL+'/getprofile/'+id)
  }


  public clearLoggedUser(){
    this.isAuthenticated$.next()
    this._cacheUserSource.complete()//(new Employee(null,"","","","",null,"","",null,null,null,"","","","","","",null,null,null,null,"","",null,"",null))
  }
}
