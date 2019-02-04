import { Component, OnInit } from '@angular/core';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { Observable, Subject } from 'rxjs';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { filter, map, take, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-system-settings',
  templateUrl: './system-settings.component.html',
  styleUrls: ['./system-settings.component.css']
})
export class SystemSettingsComponent implements OnInit {

  constructor(private _employeeMasterService: EmployeeMasterService,
              private _fb: FormBuilder) { }

  users$: Observable<userXAuth[]> = new Observable<userXAuth[]>()
  changedUsers: userXAuth[] = []
  isAlive$: Subject<any> = new Subject<any>()

  ngOnInit() {    
    this._employeeMasterService.getUserAuthorities();
    this.users$ = this._employeeMasterService.userAuthSettings$
  }

  addRow(id: string, e){
    if (this.changedUsers.find(a => a.id == id)){
        this.changedUsers[this.changedUsers.findIndex(e => e.id == id)].admin = e.target.checked
      } else {
      this.changedUsers.push(new userXAuth(id,'','',e.target.checked))
    }
  }

  saveSettings(){
    this._employeeMasterService.saveUserAuthoritiesChanges(this.changedUsers)
  }

  
}
export interface userXAuth{
  id: string
  name: string
  area: string
  admin: boolean
}
export class userXAuth{
  constructor(
    id: string,
    name: string,
    area: string,
    admin: boolean
  ){
    this.id = id
    this.name = name
    this.area = area
    this.admin = admin
  }
}
