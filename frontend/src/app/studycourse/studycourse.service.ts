import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { studyCourse } from '../interfaces/study-course';
import { API_URL, ADMIN_URL } from '../url-settings';
import { Employee } from '../interfaces/employee';
import { ReplaySubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class StudycourseService {

  constructor(private _http: HttpClient) { }

  private _searchResultsSource: ReplaySubject<Employee[]> = new ReplaySubject<Employee[]>(1)
  searchResults$ = this._searchResultsSource.asObservable();

  private _detailsSource: ReplaySubject<studyCourse> = new ReplaySubject<studyCourse>(1)
  details$ = this._detailsSource.asObservable()


  insertAttempt(sc: studyCourse){
    return this._http.post<studyCourse>(API_URL+'/se/studycourse/add',sc,{observe: 'response'})
  }

  getDetails(id: string){
    return this._http.get<studyCourse>(ADMIN_URL+ '/studycourse/get?id='+id,{observe: 'response'})
    .subscribe(
      res => {
        this._detailsSource.next(res.body)
      })
  }

  searchAttempt(sf: any){
    return this._http.get<Employee[]>(ADMIN_URL+'/studycourse/search?'
    +'id='+sf.id
    +'&nm='+sf.name
    +'&kn=' +sf.kana
    +'&spo=' +sf.sponsor
    +'&exp=' +sf.expenses
    +'&st=' +sf.stdate
    +'&ed=' +sf.enddate
    +'&op='+sf.op, {observe: 'response'}).subscribe(
      res => {
        if (!res.body) {
        alert('何も見つかりません')
        return
        }
        else this._searchResultsSource.next(res.body)
      }
    )
  }
}
