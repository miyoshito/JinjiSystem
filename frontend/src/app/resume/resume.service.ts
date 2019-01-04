import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Resume, SearchForm } from './resume-details-interface';

import { ADMIN_URL } from 'src/app/url-settings'
import { takeUntil, map } from 'rxjs/operators';
import { Subject, Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { Router } from '@angular/router';
import { Employee } from '../interfaces/employee';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {

  constructor(private _httpClient: HttpClient,
              private _router: Router,
              private _loginService: LoginService) { }

  private searchSource_ = new ReplaySubject<Employee[]>()
  resumeSearchResult$ = this.searchSource_.asObservable();

  

  saveResumeAttempt(resume: Resume){
    return this._httpClient.post<Resume>(ADMIN_URL+'/resume/save', resume, { observe: 'response' }).subscribe(
      response =>{},
      error => {
        alert('something bad happened...')
      })
  }

  softDeleteDetail(type: string, id: number){
    return this._httpClient.put<any>(ADMIN_URL + '/resume/delete?type='+type+'&id='+id, null, {
      observe: 'response'
    })
  }

  searchResumeAttempt(searchParam: SearchForm){
    return this._httpClient.get<Employee[]>(ADMIN_URL
      + '/resume/search?i='+ searchParam.id
      + '&n=' + searchParam.name
      + '&k=' + searchParam.kana
      + '&r=' + searchParam.recruit
      + '&a=' + searchParam.age
      + '&st=' + searchParam.study
      + '&b=' + searchParam.bunri
      + '&ca=' + searchParam.career
      + '&qq=' + searchParam.qualification
      ,{observe: 'response'})
  }
  sendSearchResults(list: Employee[]){
    this.searchSource_.next(list)
  }

}
