import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { StudycourseService } from '../studycourse.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-study-course-results',
  templateUrl: './study-course-results.component.html',
  styleUrls: ['./study-course-results.component.css']
})
export class StudyCourseResultsComponent implements OnInit {

  constructor(private _scService: StudycourseService,
              private _router: Router
    ) { }

  results$: Observable<Employee[]> = new Observable<Employee[]>()

  ngOnInit() {
    this.results$ = this._scService.searchResults$
  }

  details(uid: string, scid: string){
    console.log('/admin/studycourse/details/'+uid+'/'+scid)
    this._scService.getDetails(scid)
    this._router.navigate(['/admin/studycourse/details/'+uid+'/'+scid])
  }

}
