import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { ProfileService } from 'src/app/services/profile.service';
import { Subscription, Observable, Subject } from 'rxjs';
import { Employee, Curriculum } from 'src/app/interfaces/employee';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { takeUntil, map } from 'rxjs/operators';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

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
  displayButton: boolean
  user: Employee  
  isAlive$: Subject<boolean> = new Subject<boolean>()
  constructor(private _profileService: ProfileService,
              private _broadcastService: BroadcastService,
              private _employeeService: EmployeeMasterService,
              private _router: Router,
              private _route: ActivatedRoute,
              private _curriculumService: CurriculumService) { }

  ngOnInit() {    
    if (this._router.url.startsWith('/admin')) {
      this.shainid = this._route.snapshot.paramMap.get('id')
      this.admin$ = true
      this.displayButton = true
      this._employeeService.getShainData(this.shainid,true,false,false)
      this.profileSelected$ = this._employeeService.employee$            
    } else {
      this.admin$ = false
      this.displayButton = false
      this._profileService.getLoggedInUserData()
      this.profileSelected$ = this._employeeService.employee$
      this._profileService.cachedUser$.pipe(
        takeUntil(this.isAlive$),
        map(e => {
          this._employeeService.getShainData(e.id, true, false, false)
        })
      ).subscribe()
      this.profileSelected$.pipe(takeUntil(this.isAlive$), map(usr =>{
        this.shainid = usr.shainId
      })).subscribe()
    }
  }
  
  ngOnDestroy(){
    this.isAlive$.next();
  }

  sumOf(cv?: Curriculum[]): string{
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
    this._router.navigate(['profile/shokumurirekisho/edit/'+this.shainid+'/'+id])
  }

  addNewSR(){
    this._broadcastService.userAuthorization$.pipe(takeUntil(this.isAlive$), map(auth =>{
      if (auth === 'ADMIN') {
        this._router.navigate(['admin/shokumurirekisho/'+this.shainid+'/add'])
      } else this._router.navigate(['profile/shokumurirekisho/add'])
    })).subscribe()
  }

}
