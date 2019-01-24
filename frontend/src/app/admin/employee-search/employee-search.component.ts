import { Component, OnInit } from '@angular/core';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Observable, Subject } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-employee-search',
  templateUrl: './employee-search.component.html',
  styleUrls: ['./employee-search.component.css']
})
export class EmployeeSearchComponent implements OnInit {

  params$: Observable<any[]>
  
  searchForm: FormGroup

  map: Map<string, string> = new Map<string, string>()

  displayIncludeBox: boolean
  includeRetired: boolean

  isAlive$: Subject<boolean> = new Subject<boolean>()
  

  constructor(private _employeeService: EmployeeMasterService,
              private _fb: FormBuilder,
              private _broadcastService: BroadcastService) { }

  ngOnInit() {
    this.params$ = this._employeeService.getViewRendering()
    this.buildForm()
    this.checkIfSoumu()
  }

  testSearch(){
    this.map.clear()
    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => this.map.set(k,this.searchForm.value[k]));

      this.includeRetired ? this.map.set('retired', 'true') : this.map.set('retired', 'false');
      this._employeeService.searchShain(this.map)
  }

  includeRetiredShains(e){
    this.includeRetired = e.target.checked
  }

  checkIfSoumu(){
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {
        if (groups.find(e => e.id == 3)){
          this.displayIncludeBox = true
        }
      })).subscribe()

  }
  ngOnDestroy(): void {
    this.isAlive$.next()
  }



  buildForm(){
    this.searchForm = this._fb.group({
      id: [''],
      name: [''],
      kana: [''],
      affiliation: [[]]
    })
  }

}

export interface searchParam{
  id: string,
  name: string,
  kana: string,
  affiliation: number[]
}
