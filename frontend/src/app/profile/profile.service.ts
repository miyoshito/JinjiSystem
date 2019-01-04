import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, Subject, Subscription, AsyncSubject } from 'rxjs';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';

import { API_URL, ADMIN_URL } from '../url-settings'
import { AuthService } from '../guards/auth.service';
import { Employee, MinEmployee } from '../interfaces/employee';
import { map, tap, takeUntil } from 'rxjs/operators';
import { BroadcastService } from '../broadcast.service';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  employee$: Observable<MinEmployee>
  isAuthenticated$: Subject<any>

  private _cacheUserSource: ReplaySubject<MinEmployee> = new ReplaySubject<MinEmployee>(1)
  cachedUser$ = this._cacheUserSource.asObservable()

  constructor(private _http: HttpClient,
              private _broadcastService: BroadcastService,
              private _loginService: LoginService
              ) { }

  //returns only the infos you need to stay logged in
  public getLoggedInUserData(){
    return this._http.get<MinEmployee>(API_URL+'/se/getmyinfos',{observe: 'response'}).subscribe(data =>{
    this._cacheUserSource.next(data.body)
    this._broadcastService.pushAuthentication(true)
    this._broadcastService.pushAuthorization(data.body.role)
    },
    err =>{    
      this._loginService.logout()
    })
  }
  
  public clearLoggedUser(){
    this.isAuthenticated$.next()
    this._cacheUserSource.complete()//(new Employee(null,"","","","",null,"","",null,null,null,"","","","","","",null,null,null,null,"","",null,"",null))
  }
}
