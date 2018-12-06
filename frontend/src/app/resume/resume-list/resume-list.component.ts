import { Component, OnInit, ViewChild } from '@angular/core';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ResumeService } from '../resume.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-resume-list',
  templateUrl: './resume-list.component.html',
  styleUrls: ['./resume-list.component.css']
})
export class ResumeListComponent implements OnInit {

  displayedColumns = ['社員番号','氏名','採用区分','出身校','資格','詳細']

  @ViewChild(MatPaginator) paginator: MatPaginator
  dataSource = new MatTableDataSource()

  
  constructor(private _employeeService: EmployeeMasterService,
              private _resumeService: ResumeService,
              private _router: Router
              ) { }


  ngOnInit() {
    this._employeeService.getEmployeeList().subscribe(list =>{
      this.dataSource.data = list

      list.forEach(val=>{
        console.log(val)
      })
    })
  }

  editRirekisho(id: number){
    this._router.navigate(['/admin/rirekisho/edit/'+id])
  }

  nestedFilterCheck(search, data, key) {
    if (typeof data[key] === 'object') {
      for (const k in data[key]) {
        if (data[key][k] !== null) {
          search = this.nestedFilterCheck(search, data[key], k);
        }
      }
    } else {
      search += data[key];
    }
    return search;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    /*this.dataSource.filterPredicate = (data, filterValue: string)  => {
      const accumulator = (currentTerm, key) => {
        return this.nestedFilterCheck(currentTerm, data, key);
      };
      const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
      // Transform the filter by converting it to lowercase and removing whitespace.
      const transformedFilter = filterValue.trim().toLowerCase();
      return dataStr.indexOf(transformedFilter) !== -1;
    };*/

  }

  ngAfterViewInit(){
    this.dataSource.paginator = this.paginator
  }

}
