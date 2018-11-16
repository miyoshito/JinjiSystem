import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject,  ReplaySubject,} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BroadcastService {

  constructor() { }

  _userAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  _UserAuthorizations: ReplaySubject<string> = new ReplaySubject<string>()

  userAuthenticated$ = this._userAuthenticated.asObservable();

  userAuthorization$ = this._UserAuthorizations.asObservable();

  pushAuthentication(auth: boolean){
    this._userAuthenticated.next(auth)
  }
  pushAuthorization(auth: string){
    this._UserAuthorizations.next(auth)    
  }

}
