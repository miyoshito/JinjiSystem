import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Observable, Subject, BehaviorSubject } from "rxjs";
import { AUTH_URL } from '../url-settings'
import { User } from './login.interface'
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';
import { BroadcastService } from '../broadcast.service';
import { tap, catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http: HttpClient,
              private broadcastService: BroadcastService) { }

  private loggedUser: EmployeeMasterComponent

  doLogin(authuser: User){
    return this._http.post<any>(AUTH_URL+'/login?username=' + authuser.username + '&password=' + authuser.password, null,
      {
        observe: 'response'
      })
    }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');    
  }
}