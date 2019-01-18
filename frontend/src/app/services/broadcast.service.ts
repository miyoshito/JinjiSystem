import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject,  ReplaySubject,} from 'rxjs';
import { Employee, MinEmployee, group } from 'src/app/interfaces/employee';

@Injectable({
  providedIn: 'root'
})

/**
 * This service tracks if user is logged in and his role when he reload the page.
 * All the information is based on token contents.
 */
export class BroadcastService {

  constructor() { }

private  _userAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

private  _UserAuthorization: ReplaySubject<boolean> = new ReplaySubject<boolean>(1)

private _UserGroup: ReplaySubject<group[]> = new ReplaySubject<group[]>(1)

  userAuthenticated$ = this._userAuthenticated.asObservable();

  userAuthorization$ = this._UserAuthorization.asObservable();

  userGroup$ = this._UserGroup.asObservable();

  pushAuthentication(auth: boolean){
    this._userAuthenticated.next(auth)
  }
  
  pushAuthorization(auth: boolean){
    this._UserAuthorization.next(auth)    
  }

  pushGroup(auth: group[]){
    this._UserGroup.next(auth)
  }

}
