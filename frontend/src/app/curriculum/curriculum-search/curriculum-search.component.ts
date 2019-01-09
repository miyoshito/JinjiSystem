import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumService } from '../curriculum.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

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

  constructor(private curriculumService: CurriculumService,
              private _fb: FormBuilder,
              private _router: Router) {
                this.validExp=true;
               }

  ngOnInit() {
    for (let i = 18; i <= 90; i++){
      this.age.push(i)
    }
    this.buildSearchForm()
    this.data$ = this.curriculumService.getPropertiesList()    
    this.industry$ = this.curriculumService.getBusinessLogic()
  }

  doSearch(){
    if (!this.validExp){
      return;
    }

    let map: Map<string, string> = new Map<string, string>()

    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => map.set(k,this.searchForm.value[k]));

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


export interface cvSearchForm{
  id: string,
  name: string,
  kana: string,
  recruit: string,
  age: string
  operator: string
  experience: string
  customerName: string,
  indType: string,
  targetBusiness: string,
  dbms: number[]
  os: number[]
  lang: number[]
  tools: number[]
  response: number[]
  maker: number[]
  role: string
}
