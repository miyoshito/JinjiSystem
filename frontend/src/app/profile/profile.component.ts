import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { EmployeeMasterComponent } from '../admin/employee-master/employee-master.component';
import { takeUntil, takeWhile, map } from 'rxjs/operators';
import { Observable, Subject, BehaviorSubject, Subscription } from 'rxjs';
import { BroadcastService } from '../broadcast.service';
import { Employee } from '../interfaces/employee';
import { MAT_SORT_HEADER_INTL_PROVIDER } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';

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
              private _route: ActivatedRoute) {
               }

  loggedUser$: Observable<Employee>
  profileSelected$: Observable<Employee>
  admin$: boolean
  shainid: string
  user: Employee

  sub: Subscription
  

  ngOnInit() {

    this.isLoggedIn$ = this._broadcastService.userAuthenticated$
    
    this.sub = this._route.parent.parent.url.subscribe( url => {
      if (url[0].path === 'admin'){ //if you're navigating from /admin/ path        
        this.shainid = this._route.snapshot.paramMap.get('id')
        this.admin$ = true        
        this.profileSelected$ = this._profileService.getUserProfile(this.shainid)
        this.profileSelected$.subscribe(data =>{
          this.user = data
        })
      } else { //any another route that casts /profile/
        this.admin$ = false
        this.profileSelected$ = this._profileService.cachedUser$
        }
      })    
  }

  ngOnDestroy(){
    console.log('just letting you know im unsubscribing.')
    this.sub.unsubscribe()
  }

  addResume(){
    this._router.navigate(['/admin/profile/'+this.shainid+'resume/add'])
  }
}
    

