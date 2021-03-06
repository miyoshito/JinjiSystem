import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { Router } from '@angular/router';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Subject, Observable } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { BroadcastService } from 'src/app/services/broadcast.service';

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
    private _employeeService: EmployeeMasterService,
    private _broadcastService: BroadcastService) {
    this.localeService.use('ja')
  }

  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial<BsDatepickerConfig>;
  stValue = new Date()
  edValue = new Date()

  sponsorsList$: string[]
  educationList$: string[]

  includeRetired: boolean

  isAlive$: Subject<boolean> = new Subject<boolean>()
  displayIncludeBox: boolean

  map: Map<string, string> = new Map<string, string>()

  ngOnInit() {
    this.checkIfSoumu()
    this.title = "教育受講履歴検索画面"
    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD' });
    this.buildForm()

    this.searchForm.valueChanges.subscribe(val => {
      if (val.stdate != '') {
        this.stValue = this.searchForm.get('stdate').value
      }
      if (val.enddate != '') {
        this.edValue = this.searchForm.get('enddate').value
      }
    })

    this._scService.getEducationsList().pipe(takeUntil(this.isAlive$), map(res => {
      this.educationList$ = res.body
    })).subscribe()

    this._scService.getSponsorList().pipe(takeUntil(this.isAlive$), map(res => {
      this.sponsorsList$ = res.body
    })).subscribe()

  }

  includeRetiredShains(e) {
    this.includeRetired = e.target.checked
  }

  checkIfSoumu() {
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {
        if (groups.find(e => e.id == 3)) {
          this.displayIncludeBox = true
        }
      })).subscribe()
  }

  searchAttempt() {
    this.map.clear()
    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => {
        this.map.set(k, this.searchForm.value[k])
      })
      
    this.includeRetired ? this.map.set('retired', 'true') : this.map.set('retired', 'false');
    if (this.map.get("stdate")) this.map.set("stdate", this.stValue.toISOString().substring(0, 10))
    if (this.map.get("enddate")) this.map.set("enddate", this.edValue.toISOString().substring(0, 10))

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

    this._scService.searchAttempt(this.map).pipe(
      takeUntil(this.isAlive$),
      map(res => {
        if (!res.body || res.body.length < 1) {
          alert('データーが見つかりません')
          return
        } else if (res.body.length == 1) {
          this._employeeService.getShainData(res.body[0].employee_id, "edu")
          this._router.navigate(['/public/studycourse/details/' + res.body[0].employee_id])
        } else {
          this._scService.pushSearchResults(res.body)
          this._router.navigate(['/public/studycourse/list'])
        }
      })).subscribe()
  }

  reset() {
    this.searchForm.reset()
    this.buildForm()
  }

  ngOnDestroy(): void {
    this.isAlive$.next()
  }

  buildForm() {
    this.searchForm = this._fb.group({
      id: [''],
      name: [''],
      kana: [''],
      sponsor: [''],
      educationName: [''],
      expenses: [''],
      stdate: [''],
      enddate: [''],
      op: ['']
    }, { validator: atLeastOne(Validators.required) })
  }
}
