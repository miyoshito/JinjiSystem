import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Resume } from './resume-details-interface';

import { ADMIN_URL } from 'src/app/url-settings'
import { takeUntil } from 'rxjs/operators';
import { Subject, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {

  constructor(private _httpClient: HttpClient,
              private _router: Router) { }

  saveResumeAttempt(resume: Resume){
    return this._httpClient.post<any>(ADMIN_URL+'/resume/save', resume, {
      observe: 'response'
    })
  }

  softDeleteDetail(type: string, id: number){
    return this._httpClient.put<any>(ADMIN_URL + '/resume/delete?type='+type+'&id='+id, null, {
      observe: 'response'
    })
  }

}
