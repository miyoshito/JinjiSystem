import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Employee } from 'src/app/interfaces/employee';
import { Observable } from 'rxjs';
import { ProfileService } from 'src/app/services/profile.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees$ = new Observable<Employee[]>();

  constructor(private employeeService: EmployeeMasterService,
              private _router: Router) { }

  ngOnInit() {    
    this.employees$ = this.employeeService.searchResults$
  } 
  
  editShain(id: string){
    console.log('/admin/profile/'+id+'/edit')
    this._router.navigate(['/admin/profile/'+id+'/edit'])
  }

}
