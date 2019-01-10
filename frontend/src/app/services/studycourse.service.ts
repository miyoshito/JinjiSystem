import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { studyCourse } from 'src/app/interfaces/study-course';
import { API_URL, ADMIN_URL } from 'src/app/url-settings';
import { Employee } from 'src/app/interfaces/employee';
import { ReplaySubject } from 'rxjs';
import { map } from 'rxjs/operators';


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
    return this._http.post<studyCourse>(API_URL+'/se/studycourse/add',sc,{observe: 'response'}).subscribe()
  }

  getDetails(id: string){
    return this._http.get<studyCourse>(ADMIN_URL+ '/studycourse/get?id='+id,{observe: 'response'})
    .subscribe(
      res => {
        this._detailsSource.next(res.body)
      })
  }

  searchAttempt(map: Map<string, string>){
    let param: HttpParams = new HttpParams()

    map.forEach((k,v) =>{
      param = param.append(v,k)
    })
    return this._http.get<Employee[]>(ADMIN_URL+'/studycourse/search',{params: param, observe: 'response'})
  }

  pushSearchResults(emp: Employee[]){
    this._searchResultsSource.next(emp)
  }

}
