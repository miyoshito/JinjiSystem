import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { takeUntil, map } from 'rxjs/operators';


@Component({
  selector: 'app-curriculum-search',
  templateUrl: './curriculum-search.component.html',
  styleUrls: ['./curriculum-search.component.css']
})
export class CurriculumSearchComponent implements OnInit {

  data$: Observable<any>
  industry$: Observable<any>

  searchForm: FormGroup
  age = new Array
  validExp: boolean

  isAlive$: Subject<boolean> = new Subject<boolean>()
  displayIncludeBox: boolean

  includeRetired: boolean
  map: Map<string, string> = new Map<string, string>()

  params$: Observable<any>

  constructor(private curriculumService: CurriculumService,
    private _fb: FormBuilder,
    private _employeeService: EmployeeMasterService,
    private _router: Router,
    private _broadcastService: BroadcastService) {
    this.validExp = true;
  }

  ngOnInit() {
    this.params$ = this._employeeService.getViewRendering()
    this.buildSearchForm()
    this.checkIfSoumu()
    this.data$ = this.curriculumService.getPropertiesList()
    this.industry$ = this.curriculumService.getBusinessLogic()
  }

  includeRetiredShains(e) {
    this.includeRetired = e.target.checked
  }

  doSearch() {
    this.map.clear()
    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => this.map.set(k, this.searchForm.value[k]));
    this.includeRetired ? this.map.set('retired', 'true') : this.map.set('retired', 'false');
    //temporary until i figure out some way to validate exp and operator fields.
    if (this.map.get("operator")) this.map.delete("operator")
    if (this.map.get("experience")) {
      if (this.searchForm.controls.operator.value != '') {
        let s = this.searchForm.controls.operator.value + this.searchForm.controls.experience.value
        this.map.set("experience", s)
      } else {
        let s = 'eq' + this.searchForm.controls.experience.value
        this.map.set("experience", s)
      }
    }
    this.curriculumService.searchShokumuRireki(this.map)
  }
  // in case of clear button, sets all fields to '' for search filter purposes
  reset() {
    this.searchForm.reset()
    this.buildSearchForm()
  }
  resetField(control: string) {
    this.searchForm.controls.control.reset()
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
  ngOnDestroy(): void {
    this.isAlive$.next()
  }
  buildSearchForm() {
    this.searchForm = this._fb.group({
      id: [''],
      name: [''],
      kana: [''],
      recruit: [''],
      age: [''],
      operator: [''],
      experience: [''],
      customerName: [''],
      indType: [''],
      targetBusiness: [''],
      dbms: [[]],
      os: [[]],
      lang: [[]],
      tools: [[]],
      response: [[]],
      maker: [[]],
      role: ['']
    })
  }
}