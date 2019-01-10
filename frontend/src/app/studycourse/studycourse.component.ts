import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { Employee } from '../interfaces/employee';
import { Observable, Subject } from 'rxjs';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router } from '@angular/router';
import { takeUntil, map } from 'rxjs/operators';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-studycourse',
  templateUrl: './studycourse.component.html',
  styleUrls: ['./studycourse.component.css']
})
export class StudycourseComponent implements OnInit {

  constructor(private _profileService: ProfileService,
              private _scService: StudycourseService,
              private _router: Router,
              private _employeeService: EmployeeMasterService) { }

  cachedUser$: Observable<Employee>

  isAlive$: Subject<boolean> = new Subject<boolean>()

  ngOnInit() {
    this._profileService.cachedUser$.pipe(
      takeUntil(this.isAlive$),
      map(e => {
        this._employeeService.getShainData(e.id,false,false,true)
      })
    ).subscribe()
    this.cachedUser$ = this._employeeService.employee$
  }

  insertNewStudy(){
    this._router.navigate(['/profile/studycourses/add'])
  }

}
