import { Component, OnInit } from '@angular/core';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-employee-search',
  templateUrl: './employee-search.component.html',
  styleUrls: ['./employee-search.component.css']
})
export class EmployeeSearchComponent implements OnInit {

  params$: Observable<any[]>
  
  searchForm: FormGroup

  map: Map<string, string> = new Map<string, string>()

  constructor(private _employeeService: EmployeeMasterService,
              private _fb: FormBuilder) { }

  ngOnInit() {
    this.params$ = this._employeeService.getViewRendering()
    this.buildForm()
  }

  testSearch(){
    this.map.clear()
    Object.keys(this.searchForm.value)
      .filter(f => this.searchForm.value[f] != '')
      .forEach(k => this.map.set(k,this.searchForm.value[k]));

      this._employeeService.searchShain(this.map)
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
