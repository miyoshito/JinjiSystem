import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';


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

  params$: Observable<any>

  constructor(private curriculumService: CurriculumService,
              private _fb: FormBuilder,
              private _employeeService: EmployeeMasterService,
              private _router: Router) {
                this.validExp=true;
               }

  ngOnInit() {    
    this.params$ = this._employeeService.getViewRendering()
    this.buildSearchForm()
    this.data$ = this.curriculumService.getPropertiesList()    
    this.industry$ = this.curriculumService.getBusinessLogic()
  }

  doSearch(){    
    let map: Map<string, string> = new Map<string, string>()

    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => map.set(k,this.searchForm.value[k]));
      //temporary until i figure out some way to validate exp and operator fields.
      if(map.get("operator")) map.delete("operator")
      if(map.get("experience")){
        if (this.searchForm.controls.operator.value != ''){
        let s = this.searchForm.controls.operator.value+this.searchForm.controls.experience.value
        map.set("experience", s)
        } else {
        let s = 'eq'+this.searchForm.controls.experience.value
        map.set("experience", s)
        }
      }
    this.curriculumService.searchShokumuRireki(map)
  }
  // in case of clear button, sets all fields to '' for search filter purposes
  reset(){
    this.searchForm.reset()
    this.buildSearchForm()
  }
  resetField(control: string){
    this.searchForm.controls.control.reset()
  }
  buildSearchForm(){
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