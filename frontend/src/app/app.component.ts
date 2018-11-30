import { Component, OnInit } from '@angular/core';
import { AuthService } from './guards/auth.service';
import { RoleGuardService } from './guards/role-guard.service';
import { BroadcastService } from './broadcast.service';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { Employee } from './interfaces/employee';
import { ProfileService } from './profile/profile.service';

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
              private _route: Router,
              private _authService: AuthService,
              private _profileService: ProfileService){}

  
  
  ngOnInit(): void {
    this._broadcastService.userAuthenticated$.subscribe((val) =>{
      this.isLoggedIn$ = val
    })
      if (this._authService.isAuthenticated()){
        this._profileService.cacheUser()
        this._broadcastService.pushAuthentication(true)
      }
  }

  authValidate(){
    if (localStorage.getItem('currentUser') != null) {
      this._broadcastService.pushAuthentication(true);
    } else {
      this._broadcastService.pushAuthentication(false);
      this._route.navigate(['login'])
    }
  }

}
