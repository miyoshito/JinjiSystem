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

  private _searchResultsSource: ReplaySubject<Employee[]> = new ReplaySubject<Employee[]>()
  searchResults$ = this._searchResultsSource.asObservable();

  insertAttempt(sc: studyCourse){
    return this._http.post<studyCourse>(API_URL+'/se/studycourse/add',sc,{observe: 'response'})
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
        console.log(res.body)
        if (!res.body) {
        alert('何も見つかりません')
        return
        }
        else this._searchResultsSource.next(res.body)
      }
    )
  }
}
