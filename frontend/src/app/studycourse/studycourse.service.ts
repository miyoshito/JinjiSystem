import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { studyCourse } from '../interfaces/study-course';
import { API_URL } from '../url-settings';


@Injectable({
  providedIn: 'root'
})
export class StudycourseService {

  constructor(private _http: HttpClient) { }

  insertAttempt(sc: studyCourse){
    return this._http.post<studyCourse>(API_URL+'/se/studycourse/add',sc,{observe: 'response'})
  }
}
