import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Resume, SearchForm } from 'src/app/interfaces/resume-details-interface';

import { ADMIN_URL, PUBLIC_URL } from 'src/app/url-settings'
import { takeUntil, map } from 'rxjs/operators';
import { Subject, Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { Router } from '@angular/router';
import { Employee } from 'src/app/interfaces/employee';
import { LoginService } from 'src/app/services/login.service';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {

  constructor(private _httpClient: HttpClient,
              private _router: Router,
              private _loginService: LoginService) { }

  private searchSource_ = new ReplaySubject<Employee[]>(1)
  resumeSearchResult$ = this.searchSource_.asObservable();

  

  getSearchableResParams(){
    return this._httpClient.get<any[]>(ADMIN_URL + '/rirekisearchlist')
  }
  

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

  retrieveAllResumes(){
    return this._httpClient.get<Employee[]>(ADMIN_URL + '/resume/getall', {observe: 'response'})
  }

  searchResumeAttempt(map: Map<string, string>){
    let params: HttpParams = new HttpParams()
    map.forEach((k,v) =>{
      params = params.append(v,k)
    })    
    return this._httpClient.get<Employee[]>(ADMIN_URL+'/resume/search',{params: params, observe: 'response'})
  }
  
  sendSearchResults(list: Employee[]){
    this.searchSource_.next(list)
  }

}
