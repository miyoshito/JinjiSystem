import { Component, OnInit, ViewChild } from '@angular/core';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-resume-list',
  templateUrl: './resume-list.component.html',
  styleUrls: ['./resume-list.component.css']
})
export class ResumeListComponent implements OnInit {

  displayedColumns = ['社員番号','氏名','採用区分','出身校','資格', '退職', '削除', ' ']

  @ViewChild(MatPaginator) paginator: MatPaginator
  dataSource = new MatTableDataSource()

  
  constructor(private _employeeService: EmployeeMasterService,
              ) { }


  ngOnInit() {
    this._employeeService.getEmployeeList().subscribe(list =>{
      this.dataSource.data = list
    })
  }

  ngAfterViewInit(){
    this.dataSource.paginator = this.paginator
  }

}
