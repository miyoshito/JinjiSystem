import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.css']
})
export class AdminHeaderComponent implements OnInit {

  @Input('loggedUser')
  loggedUser$: Observable<Employee>
  
  @Input('isLoggedIn')
  isLoggedIn$: Observable<boolean>  

  @Output('logout')
  logout = new EventEmitter<boolean>()

  constructor(private _profileService: ProfileService,
    private _broadcastService: BroadcastService,
    private _route: Router,
    private _loginService: LoginService) { }

  ngOnInit() {
  }

  doLogout(){
    this.logout.emit(true)
  }



}
