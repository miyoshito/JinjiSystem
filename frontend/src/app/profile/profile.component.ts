import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';
import { takeUntil, takeWhile, map } from 'rxjs/operators';
import { Observable, Subject, BehaviorSubject, Subscription } from 'rxjs';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Employee } from '../interfaces/employee';
import { MAT_SORT_HEADER_INTL_PROVIDER } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isLoggedIn$: Observable<boolean>

  constructor(private _profileService: ProfileService,
              private _broadcastService: BroadcastService,
              private _router: Router,
              private _route: ActivatedRoute,
              private _employeeService: EmployeeMasterService) {
               }

  loggedUser$: Observable<Employee>
  profileSelected$: Observable<Employee>
  admin$: boolean
  shainid: string
  user: Employee

  isAlive$: Subject<boolean> = new Subject<boolean>()

  sub: Subscription
  

  ngOnInit() {

    this.isLoggedIn$ = this._broadcastService.userAuthenticated$
    
    this.sub = this._route.parent.parent.url.subscribe( url => {
      if (url[0].path === 'admin'){ 
        this.shainid = this._route.snapshot.paramMap.get('id')
        this.admin$ = true        
        this.profileSelected$.subscribe(data =>{
          this.user = data
        })
      } else { 
        this.admin$ = false
        this._profileService.cachedUser$.pipe(
          takeUntil(this.isAlive$),
          map(e => {
            this._employeeService.getShainData(e.id)
          })
        ).subscribe()
        this.profileSelected$ = this._employeeService.employee$
        }
      })    
  }

  ngOnDestroy(){
    this.sub.unsubscribe()
    this.isAlive$.next()
  }

  addResume(){
    this._router.navigate(['/admin/profile/'+this.shainid+'resume/add'])
  }
}
    

