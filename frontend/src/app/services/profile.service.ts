import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, ReplaySubject, Subject, Subscription, AsyncSubject } from 'rxjs';
import { API_URL, ADMIN_URL } from 'src/app/url-settings'
import { Employee, MinEmployee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { LoginService } from 'src/app/services/login.service';



@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  employee$: Observable<MinEmployee>

  private _cacheUserSource: ReplaySubject<MinEmployee> = new ReplaySubject<MinEmployee>(1)
  cachedUser$ = this._cacheUserSource.asObservable()

  constructor(private _http: HttpClient,
    private _broadcastService: BroadcastService,
    private _loginService: LoginService
  ) { }

  //returns only the infos you need to stay logged in
  public getLoggedInUserData() {
    return this._http.get<MinEmployee>(API_URL + '/se/getmyinfos', { observe: 'response' }).subscribe(data => {
      this._cacheUserSource.next(data.body)
      this._broadcastService.pushAuthentication(true)
      this._broadcastService.pushAuthorization(data.body.admin)
      this._broadcastService.pushGroup(data.body.group)
    },
      err => {
        this._loginService.logout()
      })
  }
}
