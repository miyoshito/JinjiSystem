import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, 
              private auth: AuthService
              ) { }

    canActivate() {
      //validating only expiration date
      if (!this.auth.isAuthenticated()) {
        this.router.navigate(['login'])        
        localStorage.removeItem('currentUser')
        return false;
      }
      return true;
    }
  }
