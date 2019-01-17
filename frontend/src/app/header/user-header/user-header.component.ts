import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css']
})
export class UserHeaderComponent implements OnInit {

  constructor(private _broadcastService: BroadcastService,
    ) { }

  
  @Input('loggedUser')
  loggedUser$: Observable<Employee>
  
  @Input('isLoggedIn')
  isLoggedIn$: Observable<boolean>  

  @Output('logout')
  logout = new EventEmitter<boolean>()

  displayRirekisho: boolean
  isAlive$: Subject<any> = new Subject<any>()

  ngOnInit() {
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.isAlive$),
      map(groups => {   
        groups.forEach(e =>{
          if (e.id == 3)
          this.displayRirekisho = true
        })
      })
    ).subscribe()
  }

  doLogout(){
    this.logout.emit(true)
  }

}
