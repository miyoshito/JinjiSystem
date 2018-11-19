import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Observable, Subject, BehaviorSubject } from "rxjs";
import { API_URL } from '../url-settings'
import { User } from './login.interface'

const url = API_URL
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http: HttpClient) { }

    dologin(authuser: User): Observable<any> {
    return this._http.post<any>(url+'/api/login?username=' + authuser.username + '&password=' + authuser.password, null,
      {
        observe: 'response'
      });
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('name');
  }
}