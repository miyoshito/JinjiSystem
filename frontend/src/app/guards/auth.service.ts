import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClientModule } from '@angular/common/http';
import { BehaviorSubject, Subject, ReplaySubject } from 'rxjs';
import { BroadcastService } from '../broadcast.service';



@Injectable({
  providedIn: 'root'
})
export class AuthService{

  constructor(private jwtHelper: JwtHelperService,
              private _http: HttpClientModule,
              private broadcastService: BroadcastService){}

  public isAuthenticated(): boolean{
    const token = localStorage.getItem('currentUser')
    return !this.jwtHelper.isTokenExpired(token);
  }

  getStoredToken(): string{
    return localStorage.getItem('currentUser')
  }

  decodeToken(){
    const token = localStorage.getItem('currentUser')
    return this.jwtHelper.decodeToken(token)
  }

  isTokenExpired(token: string){
    return this.jwtHelper.isTokenExpired(token)
  }
  
}
