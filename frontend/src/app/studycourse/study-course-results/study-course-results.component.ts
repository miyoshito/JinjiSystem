import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from '../studycourse.service';

@Component({
  selector: 'app-study-course-results',
  templateUrl: './study-course-results.component.html',
  styleUrls: ['./study-course-results.component.css']
})
export class StudyCourseResultsComponent implements OnInit {

  constructor(private _scService: StudycourseService) { }

  results$: Observable<Employee[]> = new Observable<Employee[]>()

  ngOnInit() {
    this.results$ = this._scService.searchResults$
  }

}
