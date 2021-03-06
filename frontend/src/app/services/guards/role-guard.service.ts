import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { LoginService } from 'src/app/services/login.service';
import { ProfileService } from 'src/app/services/profile.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public _broadcastService: BroadcastService) { }

  authority: boolean

  canActivate() {  
  this._broadcastService.userAuthorization$.pipe(
    map(auth =>{
      this.authority = auth
    })).subscribe()
  if (this.authority)
  {
    return true
  }
  else {
     return false
  }
  }

}
