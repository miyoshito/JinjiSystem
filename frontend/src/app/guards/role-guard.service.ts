import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(private jwtHelper: JwtHelperService,
              public auth: AuthService,
              public router: Router) { }

  canActivate() {
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['login'])
      localStorage.removeItem('currentUser')
      return false;
    }
    return true;
  }
}
