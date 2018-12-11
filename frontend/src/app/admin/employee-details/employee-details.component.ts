import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/profile/profile.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  constructor(private _profileService: ProfileService,
              private _route: ActivatedRoute) { }

  selectedUser$: Observable<Employee>

  ngOnInit() {
    this.selectedUser$ = this._profileService.getUserProfile(this._route.snapshot.paramMap.get('id'))
  }


}
