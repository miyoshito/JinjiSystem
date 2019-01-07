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

  constructor(private curriculumService: CurriculumService,
              private _fb: FormBuilder,
              private _router: Router) { }

  ngOnInit() {
    for (let i = 18; i <= 90; i++){
      this.age.push(i)
    }
    this.buildSearchForm()
    this.data$ = this.curriculumService.getPropertiesList()    
    this.industry$ = this.curriculumService.getBusinessLogic()
  }

  doSearch(){
    if (!this.searchForm.valid){
      this.curriculumService.getAllShokumuRireki()
    } else {
    this.curriculumService.searchShokumuRireki(this.searchForm.value)
    }
  }

  reset(){
    this.searchForm.reset()
    this.buildSearchForm()
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

    }, {validator: atLeastOne(Validators.required)})
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
