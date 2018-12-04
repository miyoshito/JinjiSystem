import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { BroadcastService } from '../broadcast.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(private jwtHelper: JwtHelperService,
              public auth: AuthService,
              public _broadcastService: BroadcastService,
              public router: Router) { }

  authority: string

  canActivate() {
    this._broadcastService.userAuthorization$.subscribe(auth =>{
      this.authority = auth
    })

    if (this.authority == 'ADMIN' || this.authority == 'SOUMU') return true
    else return false    
  }

}
