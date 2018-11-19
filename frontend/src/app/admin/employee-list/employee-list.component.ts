import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { EmployeeMasterService } from '../employee-master/employee-master.service';
import { Employee } from 'src/app/interfaces/employee';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  displayedColumns = ['社員番号','社員氏名','','','']
  dataSource = new MatTableDataSource()
  employees: Employee[];

  @ViewChild(MatPaginator) paginator: MatPaginator

  constructor(private employeeService: EmployeeMasterService) { }

  ngOnInit() {
    this.employeeService.getEmployeeList().subscribe(list =>{
      this.dataSource.data = list
    })
  }
  ngAfterViewInit(){
    this.dataSource.paginator = this.paginator
  }

}
