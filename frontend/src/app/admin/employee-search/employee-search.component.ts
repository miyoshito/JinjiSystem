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

  constructor(private _employeeService: EmployeeMasterService,
              private _fb: FormBuilder) { }

  ngOnInit() {
    this.params$ = this._employeeService.getViewRendering()
    this.buildForm()
  }

  testSearch(){
    let param: searchParam = this.searchForm.value
    try {
    this._employeeService.searchShain(param.id, param.name, param.kana, param.affiliation)
    } catch (e){
      alert('algo errado n esta certo')
    }
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
