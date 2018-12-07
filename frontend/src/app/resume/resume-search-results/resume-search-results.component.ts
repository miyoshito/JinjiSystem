import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ResumeService } from '../resume.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Qualifications } from '../resume-details-interface';

@Component({
  selector: 'app-resume-search-results',
  templateUrl: './resume-search-results.component.html',
  styleUrls: ['./resume-search-results.component.css']
})
export class ResumeSearchResultsComponent implements OnInit {

  constructor(private _resumeService: ResumeService,
              private _router: Router) { }

  results$ = new Observable<Employee[]>()

  displayQualifications: Qualifications[]

  ngOnInit() {
    this.results$ = this._resumeService.resumeSearchResult$
  }

  editRirekisho(id: number){
    this._router.navigate(['/admin/rirekisho/edit/'+id])
  }

  showRirekisho(id: number){
    this._router.navigate(['/admin/rirekisho/details/'+id])
  }

}

export interface resumeResults{
  id: string,
  name: string,
  rectype: string,
  school: string,
  qualifications: Qualifications[]
}