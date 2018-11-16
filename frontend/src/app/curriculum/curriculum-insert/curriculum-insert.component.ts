import { Component, OnInit } from '@angular/core';
import { Observable, from, forkJoin, Subscription } from 'rxjs';
import { CurriculumService } from '../curriculum.service';
import { Data } from 'src/app/interfaces/data';
import {NgSelectModule, NgOption} from '@ng-select/ng-select';
import { map, switchMap, merge, mergeMap, flatMap, filter, tap } from 'rxjs/operators';

@Component({
  selector: 'app-curriculum-insert',
  templateUrl: './curriculum-insert.component.html',
  styleUrls: ['./curriculum-insert.component.css']
})
export class CurriculumInsertComponent implements OnInit {

  constructor(private cvService: CurriculumService) {
   }



  maker$: Data[]
  selectedMakers: Data[] = []
  os$: Data[] = []
  selectedOs: Data[]
  tools$: Data[] = []
  selectedTools: Data[]
  response$: Data[] = []
  selectedResponse: Data[]
  lang$: Data[] = []
  selectedLang: Data[]
  dbms$: Data[] = []
  selectedDbms: Data[]

  eueu: Observable<Data[]>

  subscription: Subscription

  data$: Observable<Data[]>

  ngOnInit() {
    this.data$ = this.cvService.getPropertiesList()    
    //this works but need to be fixed cause gonna cause garbate in da future.
    this.data$.pipe(map(constants => constants.filter((maker) => maker.tname === 'maker'))).subscribe(maker => this.maker$ = maker)
    this.data$.pipe(map(constants => constants.filter((os) => os.tname === 'os'))).subscribe(os => this.os$ = os)
    this.data$.pipe(map(constants => constants.filter((tools) => tools.tname === 'tools'))).subscribe(tools => this.tools$ = tools)
    this.data$.pipe(map(constants => constants.filter((lang) => lang.tname === 'lang'))).subscribe(lang => this.lang$ = lang)
    this.data$.pipe(map(constants => constants.filter((dbms) => dbms.tname === 'dbms'))).subscribe(dbms => this.dbms$ = dbms)    
    this.data$.pipe(map(constants => constants.filter((response) => response.tname === 'response'))).subscribe(response => this.response$ = response)         
  }

  ngOnDestroy(): void {
  }

}
