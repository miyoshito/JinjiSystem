import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-study-course-edit',
  templateUrl: './study-course-edit.component.html',
  styleUrls: ['./study-course-edit.component.css']
})
export class StudyCourseEditComponent implements OnInit {

  constructor(private _fb: FormBuilder,
              private _studyCourseService: StudycourseService,
              private _router: Router,
              private _route: ActivatedRoute,
              private _profileService: ProfileService,
              private _employeeService: EmployeeMasterService) { }

  studyForm: FormGroup

  title: String

  isAlive$: Subject<boolean> = new Subject<boolean>()

  selectedUser$: Observable<Employee>

  ngOnInit() {
    this.buildForm()

    if (this._router.url.startsWith('/admin') && this._router.url.includes('edit')){
    this.title = "教育受講履歴編集画面"
    this.selectedUser$ = this._employeeService.employee$
    this.patchData(this.selectedUser$)
    }

    if(this._router.url.endsWith('/studycourses/add')){
    this.title = "教育受講履歴登録画面"
    }
  }

  insertAttempt(){
    this._studyCourseService.insertAttempt(this.studyForm.value)    
  }

  patchData(e: Observable<Employee>){
    e.pipe(takeUntil(this.isAlive$),
    map(em =>{
      em.educations.find(id => this._route.snapshot.paramMap.get('uscid') == id.id)
      this.studyForm.patchValue(em)      
    })).subscribe()
  }

  buildForm(){
    this.studyForm = this._fb.group({
      id: [''],
      sponsor: [''],
      educationName: [''],
      startPeriod: [''],
      endPeriod: [''],
      venue: [''],
      tuitionFee: [''],
      transportExpenses: [''],
      hotelExpenses: [''],
      overview: [''],
      active: [''],
      updated: [''],
      updatedBy: [''],
      employee_id: ['']
    })
  }



}
