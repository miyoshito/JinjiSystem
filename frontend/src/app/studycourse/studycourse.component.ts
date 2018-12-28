import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../profile/profile.service';
import { Employee } from '../interfaces/employee';
import { Observable } from 'rxjs';
import { StudycourseService } from './studycourse.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-studycourse',
  templateUrl: './studycourse.component.html',
  styleUrls: ['./studycourse.component.css']
})
export class StudycourseComponent implements OnInit {

  constructor(private _profileService: ProfileService,
              private _scService: StudycourseService,
              private _router: Router) { }

  cachedUser$: Observable<Employee>

  ngOnInit() {
    this.cachedUser$ = this._profileService.cachedUser$    
  }

  insertNewStudy(){
    this._router.navigate(['/profile/studycourses/add'])
  }

}
