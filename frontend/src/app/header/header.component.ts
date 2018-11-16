import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../guards/auth.service';
import { BroadcastService } from '../broadcast.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    loggedIn: boolean
    employeeName: string

    loggedUser: string

    constructor(private loginService: LoginService,
                private route: Router,
                private authService: AuthService,
                private broadcastService: BroadcastService) {}

  ngOnInit() {
    this.broadcastService.userAuthenticated$.subscribe(bool =>{
      this.loggedIn = bool
    })
    this.loggedUser = localStorage.getItem('name')
  }
  home(){
    this.route.navigate(['home']);
  }

  logout() {
    this.broadcastService.pushAuthentication(false)
    this.route.navigate(['/'])
    this.loginService.logout()
  }

}
