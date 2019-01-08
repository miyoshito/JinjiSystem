import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from '../studycourse.service';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { Router } from '@angular/router';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';

@Component({
  selector: 'app-study-course-search',
  templateUrl: './study-course-search.component.html',
  styleUrls: ['./study-course-search.component.css']
})
export class StudyCourseSearchComponent implements OnInit {

  searchForm: FormGroup
  title: string
  constructor(private _fb: FormBuilder,
              private _scService: StudycourseService,
              private _router: Router,
              private localeService: BsLocaleService,
              private _employeeService: EmployeeMasterService) {
                localeService.use('ja')
               }

  bsValue: String = new Date().toISOString().slice(0,10);
  endValue: Date = new Date()
  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial<BsDatepickerConfig>;

  ngOnInit() {
    this.title="教育受講履歴検索画面"
    this.bsConfig = Object.assign(
      { minMode: this.minMode },
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MMMM/dd' },
      { dateRangeFormat: 'YYYY/MMMM/dd'});
    this.buildForm()
  }

  searchAttempt(){
  if(!this.searchForm.valid){
    this._employeeService.getAllUsers()    
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
