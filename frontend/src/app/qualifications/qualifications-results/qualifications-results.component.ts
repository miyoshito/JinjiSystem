import { Component, OnInit } from '@angular/core';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { QualificationsService } from 'src/app/services/qualifications.service';
import { Router } from '@angular/router';
import { Qualifications } from 'src/app/interfaces/qualifications';

@Component({
  selector: 'app-qualifications-results',
  templateUrl: './qualifications-results.component.html',
  styleUrls: ['./qualifications-results.component.css']
})
export class QualificationsResultsComponent implements OnInit {

  constructor(private _qualificationService: QualificationsService,
              private _employeeService: EmployeeMasterService,
              private _router: Router) { }

  usersResult$: Observable<Qualifications[]> = new Observable<Qualifications[]>()

  ipp: number = 5
  p: number = 1

  ngOnInit() {
    this.usersResult$ = this._qualificationService.searchResults$
  }

  async edit(uid: string, scid: number){    
    await this._employeeService.getShainData(uid, "qua")
      this._router.navigate(['/public/qualifications/'+uid+'/'+scid+'/edit'])
  }

}
