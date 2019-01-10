import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject,  ReplaySubject,} from 'rxjs';

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

private  _UserAuthorizations: ReplaySubject<string> = new ReplaySubject<string>()

  userAuthenticated$ = this._userAuthenticated.asObservable();

  userAuthorization$ = this._UserAuthorizations.asObservable();

  pushAuthentication(auth: boolean){
    this._userAuthenticated.next(auth)
  }
  
  pushAuthorization(auth: string){
    this._UserAuthorizations.next(auth)    
  }

}
