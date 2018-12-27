import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from '../studycourse.service';
import { ActivatedRoute } from '@angular/router';
import { map, filter, takeUntil } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { studyCourse } from 'src/app/interfaces/study-course';


@Component({
  selector: 'app-study-course-details',
  templateUrl: './study-course-details.component.html',
  styleUrls: ['./study-course-details.component.css']
})
export class StudyCourseDetailsComponent implements OnInit {

  constructor(private _scService: StudycourseService,
              private _route: ActivatedRoute,
              private _fb: FormBuilder) { }

  results$: Observable<Employee[]>
  isAlive$: Subject<boolean> = new Subject<boolean>()
  data: Employee
  education: studyCourse
  education$: Observable<studyCourse>
  studyForm: FormGroup

  ngOnInit() {
    this.education$ = this._scService.details$
    this.results$ = this._scService.searchResults$
    this.results$.pipe(
      takeUntil(this.isAlive$),
      map(e => {
        this.data = e.find(usr => usr.shainId === this._route.snapshot.paramMap.get('uid'))       
        e.filter(f => {
        this.education = f.educations.find(ed => ed.id == this._route.snapshot.paramMap.get('scid'))
        })
      })
    ).subscribe()
  }
}

