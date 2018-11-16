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

  canActivate(route: ActivatedRouteSnapshot): boolean {    
    const expectedRole = route.data.expectedRole
    const token = localStorage.getItem('currentUser');
    const tokenPayload = this.jwtHelper.decodeToken(token)
    if (!this.auth.isAuthenticated() || tokenPayload.role !== expectedRole) {
      this.router.navigate(['not authorized'])
      return false;
    }
    return true;
  }
}
