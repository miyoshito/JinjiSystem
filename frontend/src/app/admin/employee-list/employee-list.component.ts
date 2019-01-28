import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Employee } from 'src/app/interfaces/employee';
import { Observable } from 'rxjs';
import { ProfileService } from 'src/app/services/profile.service';
import { Router } from '@angular/router';
import * as url_config from 'src/app/url-settings';



@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  p: number = 1
  ipp: number = 5
  employees$ = new Observable<Employee[]>();

  constructor(private employeeService: EmployeeMasterService,
              private _router: Router) { }

  ngOnInit() {    
    this.employees$ = this.employeeService.searchResults$
  } 
  
  editShain(id: string){
    this._router.navigate(['/admin/profile/'+id+'/edit'])
  }

}
