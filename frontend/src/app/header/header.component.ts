import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';
import { Subscription, Observable, BehaviorSubject } from 'rxjs';
import { AuthService } from 'src/app/services/guards/auth.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Employee, MinEmployee } from '../interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    loggedIn: boolean
    employeeName: string

    loggedUser$: Observable<MinEmployee>
    isLoggedIn$: Observable<boolean>
    authorities$: Observable<String>
    disconnect$: Observable<boolean>
    
    menuStyle: string

    home$: Observable<boolean> = new Observable<boolean>()

    subs: Subscription

    constructor(private _loginService: LoginService,
                public _route: Router,
                private _broadcastService: BroadcastService,
                private _profileService: ProfileService,
                private _tokenVerify: AuthService) {

                  this.menuStyle = ''
                }

  ngOnInit() {
    this.subs = this._broadcastService.userAuthorization$.subscribe(auth =>{     
        this.menuStyle = auth
    })
    this.isLoggedIn$ = this._broadcastService.userAuthenticated$  
    this.loggedUser$ = this._profileService.cachedUser$
  }

  logout(trying: boolean) {
    if (trying){
    this._broadcastService.pushAuthentication(false)
    this.subs.unsubscribe()
    this._route.navigate(['/'])
    this._loginService.logout()
    }
  }

}
