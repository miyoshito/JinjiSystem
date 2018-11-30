import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css']
})
export class UserHeaderComponent implements OnInit {

  constructor() { }

  
  @Input('loggedUser')
  loggedUser$: Observable<Employee>
  
  @Input('isLoggedIn')
  isLoggedIn$: Observable<boolean>  

  @Output('logout')
  logout = new EventEmitter<boolean>()

  ngOnInit() {
  }

  doLogout(){
    this.logout.emit(true)
  }

}
