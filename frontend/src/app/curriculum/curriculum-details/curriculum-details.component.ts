import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BroadcastService } from 'src/app/broadcast.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { Subscription, Observable, Subject } from 'rxjs';
import { Employee, Curriculum } from 'src/app/interfaces/employee';
import { CurriculumService } from '../curriculum.service';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-curriculum-details',
  templateUrl: './curriculum-details.component.html',
  styleUrls: ['./curriculum-details.component.css']
})
export class CurriculumDetailsComponent implements OnInit {

  //global vars  
  loggedUser$: Observable<Employee>
  profileSelected$: Observable<Employee>
  admin$: boolean
  shainid: string
  user: Employee  
  isAlive$: Subject<boolean> = new Subject<boolean>()
  constructor(private _profileService: ProfileService,
              private _broadcastService: BroadcastService,
              private _router: Router,
              private _route: ActivatedRoute,
              private _curriculumService: CurriculumService) { }

  ngOnInit() {
    this._route.parent.parent.url.pipe(takeUntil(this.isAlive$), map(url =>{
      if (url[0].path === 'admin'){ //if you're navigating from /admin/ path        
      this.shainid = this._route.snapshot.paramMap.get('id')
      this.admin$ = true        
      this.profileSelected$ = this._profileService.getUserProfile(this.shainid)      
    } else { //any another route that casts /profile/
      this.admin$ = false
      this._profileService.getLoggedInUserData()
      this.profileSelected$ = this._profileService.cachedUser$
      this.profileSelected$.pipe(takeUntil(this.isAlive$), map(usr =>{
        this.shainid = usr.shainId
      })).subscribe()
    }
    })).subscribe()


  }
  ngOnDestroy(){
    this.isAlive$.next();
  }

  sumOf(cv: Curriculum[]): string{
    let total: number = 0
    let years: number = 0
    for(let c of cv){
      if(!c.deleted){
      total += c.experienceTime
      }
    }
    if(total >=12){
      years = parseInt((total/12).toFixed(0))
      let months = parseInt((total % 12).toFixed(0))
      return years + '年 ' + months + 'ヶ月'
    }
    else return total + 'ヶ月'
  }

  editSR(id: number){
    this.admin$ ? this._router.navigate(['/admin/shokumurirekisho/edit/'+this.shainid+'/'+id]) :
    this._router.navigate(['profile/shokumurirekisho/edit/'+id])
  }

  addNewSR(){
    this._broadcastService.userAuthorization$.pipe(takeUntil(this.isAlive$), map(auth =>{
      if (auth === 'ADMIN' || auth === 'SOUMU') {
        this._router.navigate(['admin/shokumurirekisho/'+this.shainid+'/add'])
      } else this._router.navigate(['profile/shokumurirekisho/add'])
    })).subscribe()
  }

}
