import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/guards/auth.service';
import { RoleGuardService } from 'src/app/services/guards/role-guard.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { Employee } from './interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  loggedUser$: Observable<Employee>
  isLoggedIn$: boolean
  authorities$: Observable<String>
  
  constructor(private _broadcastService: BroadcastService,
              private _router: Router,
              private _authService: AuthService,
              private _profileService: ProfileService,
              private _loginService: LoginService){}
  
  ngOnInit(): void {
    this._broadcastService.userAuthenticated$.subscribe(check =>{
      this.isLoggedIn$ = check
    })
    let token: string = localStorage.getItem('currentUser')
    if(!this._authService.isTokenExpired(token) || token != null){
        this._profileService.getLoggedInUserData()
    } else {
      this._loginService.logout()
    }    
  }

  authValidate(){
    let token: string = localStorage.getItem('currentUser')    
    if(this._authService.isTokenExpired(token) || token == null){
      this._broadcastService.pushAuthentication(false)
      this._router.navigate(['login'])
      this._loginService.logout()
    }
  }
}
