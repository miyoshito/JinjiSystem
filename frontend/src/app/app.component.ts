import { Component, OnInit } from '@angular/core';
import { AuthService } from './guards/auth.service';
import { RoleGuardService } from './guards/role-guard.service';
import { BroadcastService } from './broadcast.service';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor(private broadcastService: BroadcastService,
              private _route: Router){}

  authenticated$: boolean
  
  ngOnInit(): void {
    console.log(this.authenticated$)
    this.broadcastService.userAuthenticated$.subscribe(value =>{
      this.authenticated$ = value
    })
  }

  authValidate(){
    if (localStorage.getItem('currentUser') != null) {
      this.broadcastService.pushAuthentication(true);
    } else {
      this.broadcastService.pushAuthentication(false);
      this._route.navigate(['login'])
    }
  }

}
