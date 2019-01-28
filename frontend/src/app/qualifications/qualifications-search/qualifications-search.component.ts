import { Component, OnInit } from '@angular/core';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { FormBuilder, FormGroup } from '@angular/forms';
import { QualificationsService } from 'src/app/services/qualifications.service';
import { Observable, ReplaySubject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Router } from '@angular/router';
import { Employee } from 'src/app/interfaces/employee';

@Component({
  selector: 'app-qualifications-search',
  templateUrl: './qualifications-search.component.html',
  styleUrls: ['./qualifications-search.component.css']
})
export class QualificationsSearchComponent implements OnInit {

  constructor(
    private localeService: BsLocaleService,
    private fb: FormBuilder,
    private _qualificationsService: QualificationsService,
    private _employeeService: EmployeeMasterService,
    private _router: Router,
  ) { localeService.use('ja') }

  title = '資格情報検索画面';
  minMode: BsDatepickerViewMode = 'day'
  bsConfig: Partial<BsDatepickerConfig>
  includeRetired: boolean

  searchForm: FormGroup

  map: Map<string, string> = new Map<string, string>()

  isAlive$: Observable<boolean> = new Observable<boolean>()

  pullDownSLists$: Observable<any[]>


  ngOnInit() {
    this.pullDownSLists$ = this._qualificationsService.getShikakuSearchParams()

    this.buildSearchForm()

    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD'});
  }

  doSearch(){
    this.map.clear()
    Object.keys(this.searchForm.value)
    .filter(f => this.searchForm.value[f] != '')
    .forEach(k => this.map.set(k,this.searchForm.value[k]));
    this.includeRetired ? this.map.set('retired', 'true') : this.map.set('retired', 'false');    

    if (this.map.get("op")) this.map.delete("op")
    if (this.map.get("expenses")) {
      if (this.searchForm.get('op').value != '') {
        let s = this.searchForm.get('op').value + this.searchForm.controls.expenses.value
        this.map.set("expenses", s)
      } else {
        let s = 'eq' + this.searchForm.controls.expenses.value
        this.map.set("expenses", s)
      }
    }

      this._qualificationsService.searchForShikaku(this.map).pipe(
        takeUntil(this.isAlive$),
        map(res => {
          if (!res.body || res.body.length < 1) {
            alert('データーが見つかりません')
            return
          } else if (res.body.length == 1) {
            console.log('1 caboclo encontrado')
            this._employeeService.getShainData(res.body[0].shainId, "qua")
            this._router.navigate(['/public/qualifications/details/' + res.body[0].shainId])
          } else {
            this._qualificationsService.pushSearchResults(res.body)
            this._router.navigate(['/public/qualifications/list'])
          }
        })).subscribe()
  }

  includeRetiredShains(e) {
    this.includeRetired = e.target.checked
  }

  buildSearchForm(){
    this.searchForm = this.fb.group({
      sponsor: [''],
      qName: [''],
      examDate: [''],
      results: [''],
      costs: [''],
      id: [''],
      name: [''],
      kana: [''],
      op: ['']
    })
  }

}
