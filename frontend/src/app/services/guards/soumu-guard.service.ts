import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { BroadcastService } from '../broadcast.service';
import { Subject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class SoumuGuardService implements CanActivate {

  constructor(
    private _broadcastService: BroadcastService
  ) { }

  isAlive$: Subject<any> = new Subject<any>()
  isSoumu: boolean = false

  

  async canActivate() { 
    console.log('after >' +this.isSoumu)
    await this.checkGroup()
    console.log('before >' +this.isSoumu)
    
    return this.isSoumu
   }


   checkGroup(){
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {
        if (groups.filter(f => f.id == 3).length > 0) {
          this.isSoumu = true
        }        
      })
    ).subscribe()
   }
}
