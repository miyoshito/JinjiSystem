import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StudycourseService } from '../studycourse.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/profile/profile.service';

@Component({
  selector: 'app-study-course-edit',
  templateUrl: './study-course-edit.component.html',
  styleUrls: ['./study-course-edit.component.css']
})
export class StudyCourseEditComponent implements OnInit {

  constructor(private _fb: FormBuilder,
              private _studyCourseService: StudycourseService,
              private _router: Router,
              private _profileService: ProfileService) { }

  studyForm: FormGroup

  title: String

  selectedUser$: Observable<Employee>

  ngOnInit() {
    this.buildForm()
    this.title = "教育受講履歴編集画面"

    if(this._router.url.endsWith('/studycourses/add')){
    this.title = "教育受講履歴登録画面"
    this.selectedUser$ = this._profileService.cachedUser$    
    console.log('i see what u doing here')
    }


  }

  insertAttempt(){
    this._studyCourseService.insertAttempt(this.studyForm.value)    
  }

  patchData(e: Observable<Employee>){

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
