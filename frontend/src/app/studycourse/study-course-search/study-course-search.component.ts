import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from '../studycourse.service';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { Router } from '@angular/router';

@Component({
  selector: 'app-study-course-search',
  templateUrl: './study-course-search.component.html',
  styleUrls: ['./study-course-search.component.css']
})
export class StudyCourseSearchComponent implements OnInit {

  private searchForm: FormGroup
  title: string
  constructor(private _fb: FormBuilder,
              private _scService: StudycourseService,
              private _router: Router) { }

  ngOnInit() {
    this.title="教育受講履歴検索画面"
    this.buildForm()
  }

  searchAttempt(){
  if(!this.searchForm.valid){
    alert ("preencha pelo menos 1 campo ae po")
  }
  try{
  this._scService.searchAttempt(this.searchForm.value)
  this._router.navigate(['/admin/studycourse/list'])
  } catch(err) {
    return
  }
  }

  reset(){
    this.searchForm.reset()
    this.buildForm()
  }

  buildForm() {
    this.searchForm = this._fb.group({
      id: [''], 
      name: [''], 
      kana: [''],
      sponsor: [''],
      expenses: [''],
      stdate: [''],
      enddate: [''],
      op: ['']
    },{validator: atLeastOne(Validators.required)})
  }
}
