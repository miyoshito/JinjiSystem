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
              private authService: AuthService
              ) { }

  private isAuthenticated: boolean;

  canActivate(): boolean{

    if (!this.authService.isAuthenticated()){
      this.router.navigate(['/'])
      return false
    } 
    return true
    }
  }
