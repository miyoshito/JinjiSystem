import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { takeUntil, map } from 'rxjs/operators';

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
  
  isAlive$: Subject<any> = new Subject<any>();

  displayRirekisho: boolean

  constructor(private _profileService: ProfileService,
    private _broadcastService: BroadcastService,
    private _route: Router,
    private _loginService: LoginService) {
      this.displayRirekisho = false
     }

  ngOnInit() {
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {   
        groups.forEach(e =>{
          if (e.id == 3)
          this.displayRirekisho = true
        })
      })
    ).subscribe()
  }

  doLogout(){
    this.logout.emit(true)
  }



}
