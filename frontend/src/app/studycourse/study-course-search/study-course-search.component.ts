import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { Router } from '@angular/router';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Subject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';

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

  isAlive$: Subject<boolean> = new Subject<boolean>()

  map: Map<string, string> = new Map<string, string>()

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
    console.log('calling' +this.map.size)
    this.map.clear()
    console.log('after clear' +this.map.size)
    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k =>this.map.set(k,this.searchForm.value[k]))

      if(this.map.get("operator")) this.map.delete("operator")
      if(this.map.get("expenses")){
        if (this.searchForm.controls.operator.value != ''){
        let s = this.searchForm.controls.operator.value+this.searchForm.controls.expenses.value
        this.map.set("expenses", s)
        } else {
        let s = 'eq'+this.searchForm.controls.expenses.value
        this.map.set("expenses", s)
        }
      }

  this._scService.searchAttempt(this.map).pipe(
    takeUntil(this.isAlive$),
    map(res => {
      if(!res.body || res.body.length < 1){
        alert('データーが見つかりません')
        return
      } else if (res.body.length == 1) {
        this._employeeService.getShainData(res.body[0].shainId,false,false,true)
        this._router.navigate(['/admin/studycourse/details/'+res.body[0].shainId])
      } else {
        this._scService.pushSearchResults(res.body)
        this._router.navigate(['/admin/studycourse/list'])
      }
    })).subscribe()
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
