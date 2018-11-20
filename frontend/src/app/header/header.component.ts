import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { AuthService } from '../guards/auth.service';
import { BroadcastService } from '../broadcast.service';
import { Employee } from '../interfaces/employee';
import { ProfileService } from '../profile/profile.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    loggedIn: boolean
    employeeName: string

    loggedUser$: Observable<Employee>
    isLoggedIn$: Observable<boolean>

    constructor(private loginService: LoginService,
                private route: Router,
                private _broadcastService: BroadcastService,
                private _profileService: ProfileService) {}

  ngOnInit() {
    //in case of page refresh, this will be loaded anyway since header loads in every single page.
    if(!this.loggedUser$){
      this._profileService.cacheUser()
    }
    this.isLoggedIn$ = this._broadcastService.userAuthenticated$  
    this.loggedUser$ = this._profileService.cachedUser$
  }
  home(){
    this.route.navigate(['profile']);
  }

  logout() {
    this._broadcastService.pushAuthentication(false)
    this.route.navigate(['/'])
    this.loginService.logout()
  }

}
