import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpParams } from "@angular/common/http";
import { Observable, Subject, BehaviorSubject } from "rxjs";
import { AUTH_URL } from 'src/app/url-settings'
import { User } from 'src/app/interfaces/login.interface'
import { EmployeeMasterComponent } from 'src/app/admin/employee-master/employee-master.component';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { tap, catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http: HttpClient,
              private broadcastService: BroadcastService,
              private _router: Router) { }

  doLogin(authuser: User){    
    return this._http.post<User>(AUTH_URL+'/login', authuser, {observe:'response'})
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    this._router.navigate(['login'])
    localStorage.removeItem('currentUser')
    this.broadcastService.pushAuthentication(false)
    this.broadcastService.pushAuthorization(false)
  }

}