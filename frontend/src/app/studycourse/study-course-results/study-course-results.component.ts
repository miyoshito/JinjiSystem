import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from '../studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-study-course-results',
  templateUrl: './study-course-results.component.html',
  styleUrls: ['./study-course-results.component.css']
})
export class StudyCourseResultsComponent implements OnInit {

  constructor(private _scService: StudycourseService,
              private _profileService: ProfileService,
              private _router: Router,
              private _route: ActivatedRoute,
              private _employeeService: EmployeeMasterService
    ) { }

  //results$: Observable<Employee[]> = new Observable<Employee[]>()
  isAlive$: Subject<boolean> = new Subject<boolean>()

  profileUser$: Observable<Employee> = new Observable<Employee>()

  ngOnInit() {
    if (this._router.url.startsWith('/profile')){
      this._profileService.cachedUser$.pipe(takeUntil(this.isAlive$),
      map(e =>{
        this._employeeService.getShainData(e.id,false, false, true)
      })).subscribe()
      this.profileUser$ = this._employeeService.employee$
    } else if (this._router.url.endsWith('/details')) {
      this._employeeService.getShainData(this._route.snapshot.paramMap.get('id'), false, false, true)
      this.profileUser$ = this._employeeService.employee$
    }
  }

  details(uid: string, scid: string){
    console.log('/admin/studycourse/details/'+uid+'/'+scid)
    this._scService.getDetails(scid)
    this._router.navigate(['/admin/studycourse/details/'+uid+'/'+scid])
  }

}
