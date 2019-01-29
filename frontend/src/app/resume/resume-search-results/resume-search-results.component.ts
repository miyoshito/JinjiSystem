import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ResumeService } from 'src/app/services/resume.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-resume-search-results',
  templateUrl: './resume-search-results.component.html',
  styleUrls: ['./resume-search-results.component.css']
})
export class ResumeSearchResultsComponent implements OnInit {

  p: number = 1;
  ipp: number = 5
  
  constructor(private _resumeService: ResumeService,
              private _router: Router) { }

  results$ = new Observable<Employee[]>()

  ngOnInit() {
    this.results$ = this._resumeService.resumeSearchResult$
  }

  editRirekisho(id: number){
    this._router.navigate(['/soumu/rirekisho/edit/'+id])
  }

  showRirekisho(id: number){
    this._router.navigate(['/soumu/rirekisho/details/'+id])
  }

}
