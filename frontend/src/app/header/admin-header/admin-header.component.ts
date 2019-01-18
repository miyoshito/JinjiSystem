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
  displayAdminMasterMenu: boolean

  isSoumu: boolean
  isAdmin: boolean

  constructor(private _profileService: ProfileService,
    private _broadcastService: BroadcastService,
    private _route: Router,
    private _loginService: LoginService) {
      this.displayRirekisho = false
      this.displayAdminMasterMenu = false
     }

  ngOnInit() {
    this.checkIfAdmin()
    this.checkIfSoumu()
    this.displayAdminMenu()
  }

  checkIfAdmin(){
    this._broadcastService.userAuthorization$.pipe(takeUntil(this.isAlive$),
    map(auth =>{
      console.log(auth)
      this.isAdmin = auth
    })).subscribe()

  }

  checkIfSoumu(){
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {
        if (groups.find(e => e.id == 3)){
          this.displayRirekisho = true
          this.isSoumu = true
        }
      })).subscribe()
  }

  displayAdminMenu(){    
    if(this.isAdmin && this.isSoumu) {
      this.displayAdminMasterMenu = true
      console.log('authorized.....')
    }
  }

  ngOnDestroy(): void {
    this.isAlive$.next()
    
  }

  doLogout(){
    this.logout.emit(true)
  }



}
