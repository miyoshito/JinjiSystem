import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { BroadcastService } from '../broadcast.service';
import { LoginService } from '../login/login.service';
import { ProfileService } from '../profile/profile.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(private jwtHelper: JwtHelperService,
              public auth: AuthService,
              public _broadcastService: BroadcastService,
              public _profileService: ProfileService,
              public router: Router) { }

  authority: string

  canActivate() {  
  this._broadcastService.userAuthorization$.pipe(
    map(auth =>{
      this.authority = auth
    })).subscribe()
  if (this.authority === 'ADMIN' || this.authority === 'SOUMU')
  {
    return true
  }
  else {
     return false
  }
  }

}
