import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map, takeUntil } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { studyCourse } from 'src/app/interfaces/study-course';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { ProfileService } from 'src/app/services/profile.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { QualificationsService } from 'src/app/services/qualifications.service';

@Component({
  selector: 'app-qualifications-details',
  templateUrl: './qualifications-details.component.html',
  styleUrls: ['./qualifications-details.component.css']
})
export class QualificationsDetailsComponent implements OnInit {

  constructor(
    private _qualificationsService: QualificationsService,
    private _profileService: ProfileService,
    private _router: Router,
    private _route: ActivatedRoute,
    private _fb: FormBuilder,
    private _employeeService: EmployeeMasterService,
    private _broadcastService: BroadcastService
  ) { }

  user$: Observable<Employee> = new Observable<Employee>()
  isAlive$: Subject<boolean> = new Subject<boolean>()
  title: string = "教育履歴詳細画面"
  returnToList: boolean

  p: number = 1
  ipp: number = 5

  urlparam: string

  ngOnInit() {
    if (this._router.url.includes('/details/')) {
      this.returnToList = true
      this._employeeService.getShainData(this._route.snapshot.paramMap.get('uid'), "qua")
      this.user$ = this._employeeService.employee$
    }

    if (this._router.url.endsWith('/qualifications')) {
      this.returnToList = false
      this._profileService.cachedUser$.pipe(takeUntil(this.isAlive$),
        map(e => {
          this._employeeService.getShainData(e.id, "qua")
        })).subscribe()
      this.user$ = this._employeeService.employee$
    }
  }

  ngOnDestroy() {
    this.isAlive$.next();
  }

  edit(uid: number, scid: number) {
    if (this._router.url.includes('/details/')) this._router.navigate(['/public/qualifications/' + uid + '/' + scid + '/edit'])
    else this._router.navigate(['/public/qualifications/' + scid + '/edit'])
  }

  add(uid: number) {
    if (this._router.url.includes('/details/')) this._router.navigate(['/public/qualifications/' + uid + '/add'])
    else this._router.navigate(['/public/qualifications/add'])
  }

}
