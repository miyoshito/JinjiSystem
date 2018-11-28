import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/profile/profile.service';
import { BroadcastService } from 'src/app/broadcast.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/login/login.service';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.css']
})
export class AdminHeaderComponent implements OnInit {

  loggedUser$: Observable<Employee>
  isLoggedIn$: Observable<boolean>

  constructor(private _profileService: ProfileService,
    private _broadcastService: BroadcastService,
    private _route: Router,
    private _loginService: LoginService) { }

  ngOnInit() {
    if (!this.loggedUser$) {
      this._profileService.cacheUser()
    }
    this.isLoggedIn$ = this._broadcastService.userAuthenticated$
    this.loggedUser$ = this._profileService.cachedUser$
  }

  logout() {
    this._broadcastService.pushAuthentication(false)
    this._route.navigate(['/'])
    this._loginService.logout()
  }


}
