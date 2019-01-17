import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { BroadcastService } from '../broadcast.service';
import { Subject } from 'rxjs';
import {takeUntil, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService implements CanActivate {

  constructor(private _broadCastService: BroadcastService) { }

  isAlive$: Subject<any> = new Subject<any>()

  isAdmin: boolean = false
  isSoumu: boolean = false

  async canActivate(){
    await this.getAuthorization()
    if(this.isAdmin && this.isSoumu) 
    return true
    else return false
  }

  getAuthorization(){
    this._broadCastService.userGroup$.pipe(takeUntil(this.isAlive$),
    map(groups =>{
      if (groups.filter(f => f.id == 3).length > 0) this.isSoumu = true
    })).subscribe()
    this._broadCastService.userAuthorization$.pipe(takeUntil(this.isAlive$),
    map(bool =>{
      if (bool) this.isAdmin = true
    })).subscribe()
  }

}
