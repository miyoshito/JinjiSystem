import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';
import { takeUntil, takeWhile, map } from 'rxjs/operators';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { BroadcastService } from '../broadcast.service';
import { Employee } from '../interfaces/employee';
import { MAT_SORT_HEADER_INTL_PROVIDER } from '@angular/material';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isLoggedIn$: Observable<boolean>

  constructor(private _profileService: ProfileService,
              private _broadcastService: BroadcastService) {
               }

  loggedUser$: Observable<Employee>
  user: Employee
  

  ngOnInit() {
    this.isLoggedIn$ = this._broadcastService.userAuthenticated$  
    this.loggedUser$ = this._profileService.cachedUser$
    
    this.loggedUser$
    .subscribe(data =>{
        this.user = data
      })
  }
}
    

