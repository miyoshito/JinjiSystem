import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../profile/profile.service';
import { Employee } from '../interfaces/employee';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-studycourse',
  templateUrl: './studycourse.component.html',
  styleUrls: ['./studycourse.component.css']
})
export class StudycourseComponent implements OnInit {

  constructor(private _profileService: ProfileService) { }

  cachedUser$: Observable<Employee>

  ngOnInit() {
    this.cachedUser$ = this._profileService.cachedUser$    
  }

}
