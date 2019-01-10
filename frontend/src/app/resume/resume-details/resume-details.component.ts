import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-resume-details',
  templateUrl: './resume-details.component.html',
  styleUrls: ['./resume-details.component.css']
})
export class ResumeDetailsComponent implements OnInit {

  constructor(private _router: Router,
              private _profileService: ProfileService,
              private _employeeService: EmployeeMasterService,
              private _route: ActivatedRoute) { }

  userSelected$: Observable<Employee>

  ngOnInit() {
    this._employeeService.getShainData(this._route.snapshot.paramMap.get('id'),false,true,false)
    this.userSelected$ = this._employeeService.employee$
  }

  editScreen(id: number){
    this._router.navigate(['/admin/rirekisho/edit/'+id])
  }

}
