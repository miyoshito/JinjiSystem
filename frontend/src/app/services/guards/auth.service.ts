import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClientModule } from '@angular/common/http';
import { BehaviorSubject, Subject, ReplaySubject } from 'rxjs';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { ProfileService } from 'src/app/services/profile.service';
import { map, takeUntil } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class AuthService{

  unsub$: Subject<boolean> = new Subject<boolean>()

  constructor(private jwtHelper: JwtHelperService,
              private _profileService: ProfileService){}

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
