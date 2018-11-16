import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Observable, Subject, BehaviorSubject } from "rxjs";
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { User } from './login.interface'
const url = 'http://192.168.10.120:8080'
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http: HttpClient) { }

    dologin(authuser: User): Observable<any> {
    return this._http.post<any>(url+'/api/login?username=' + authuser.username + '&password=' + authuser.password, null,
      {
        observe: 'response',
        headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8', 'Displayname': 'charset=utf-8' })
      });
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('name');
  }
}