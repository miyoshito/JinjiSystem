import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { ProfileService } from 'src/app/services/profile.service';
import { takeUntil, map } from 'rxjs/operators';
import { Data } from 'src/app/interfaces/data';

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

  p: number = 1
  i: number = 0

  usersResult$: Observable<Employee[]> = new Observable<Employee[]>()

  ngOnInit() {
      this.usersResult$ = this._scService.searchResults$
  }

  increment(){
    this.i++
    let next = this.i
    return next
  }

  async edit(uid: number, scid: number){
    await this._employeeService.getShainData(uid.toString(), "edu")
      this._router.navigate(['/public/studycourse/'+uid+'/'+scid+'/edit'])
  }

  details(uid: string){
    this._router.navigate(['/public/studycourse/details/'+uid])
  }

  aff(aff?: Data[]){
    let a: Array<String> = []
    for (let i of aff){
      a.push(i.desc)
    }
    return a;
  }

}
