import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StudycourseService } from '../studycourse.service';

@Component({
  selector: 'app-study-course-edit',
  templateUrl: './study-course-edit.component.html',
  styleUrls: ['./study-course-edit.component.css']
})
export class StudyCourseEditComponent implements OnInit {

  constructor(private _fb: FormBuilder,
              private _studyCourseService: StudycourseService) { }

  studyForm: FormGroup

  title: String = "教育受講履歴編集画面"

  ngOnInit() {
    this.buildForm()
  }

  insertAttempt(){
    this._studyCourseService.insertAttempt(this.studyForm.value)    
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
