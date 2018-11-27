import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EmployeeMasterService } from './employee-master.service';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Data } from 'src/app/interfaces/data';
import { Employee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/broadcast.service';
import { ProfileService } from 'src/app/profile/profile.service';

@Component({
  selector: 'app-employee-master',
  templateUrl: './employee-master.component.html',
  styleUrls: ['./employee-master.component.css']
})
export class EmployeeMasterComponent implements OnInit {

  employeeForm: FormGroup

  
  sex: string[] = ['女','男'];
  support: string[] = ['有','無']
  married: string[] = ['有','無']
  bloodType: String[] = ['A+型','B+型','O+型','AB+型','A-型','B-型','O-型','AB-型']


  submitted: boolean
  title: string
  response$: Observable<any>
  success$: boolean

  isLoggedIn$: Observable<boolean>  
  loggedUser$: Observable<any>
  params$: Observable<any[]>
  

  constructor(private _fb: FormBuilder,
              private _employeeService: EmployeeMasterService,
              private _broadcastService: BroadcastService,
              private _profileService: ProfileService) { 
                this.success$ = false
              }

  ngOnInit() {
    this.title = 'プロフィール画面'
    this.initializeForm()
    //subscreve no broadcast pra saber se o user continua autenticado
    this.isLoggedIn$ = this._broadcastService.userAuthenticated$
    // invoca as params do individuo logado...
    this._profileService.cachedUser$.subscribe(data =>{
      this.employeeForm.patchValue(data)
      data.shainJoinedDate != '' ? this.employeeForm.patchValue({shainJoinedDate: data.shainJoinedDate.slice(0,10)}) : console.log('')
      data.shainBirthday != '' ? this.employeeForm.patchValue({shainBirthday: data.shainBirthday.slice(0,10)}) : console.log('')
      data.shainRegisterDate != '' ? this.employeeForm.patchValue({shainRegisterDate: data.shainRegisterDate.slice(0,10)}) : console.log('')
      data.shainRetiredDate != null ? this.employeeForm.patchValue({shainRetiredDate: data.shainRetiredDate.slice(0,10)}) : console.log('')      
    })
    this.params$ = this._employeeService.getViewRendering()
  }
  
  ngOnDestroy(){
    this.success$ = false    
  }

  submitForm(){
    let employee: Employee = this.employeeForm.value
    this.submitted = true
    if (this.employeeForm.invalid) {
      return;
    }
    try {
    this._employeeService.insertShainAttempt(employee)
    //this.employeeForm.reset()
    this.success$ = true
    //this.employeeForm.reset();
    //this.initializeForm()
    } catch (err) {
      throw err
    }
  }

  get f(){ return this.employeeForm.controls }


  initializeForm(){
    this.employeeForm = this._fb.group({
      shainId: [''],
      shainPassword: [''],
      shainName: ['', Validators.required],
      shainRecruit: ['', Validators.required],
      shainKana: ['', Validators.required],
      shainBirthday: [''],
      shainBloodType: [''],
      shainSex: ['', Validators.required],
      position: this._fb.group ({id: []}),
      affiliation: ['', Validators.required],
      shainSupport: [false],
      shainMarried: [false],
      shainHomePhoneNumber: [''],
      shainMobilePhoneNumber: [''],
      shainMail: [''],
      shainMobileMail: [''],
      shainPostalCode: [''],
      shainAddress: [''],
      shainArea: this._fb.group ({id:[]}),
      shainJoinedDate: ['', Validators.required],
      shainRetiredDate: [''],
      shainActive: true,
      shainCarModel: this._fb.group ({id: []}),
      role: this._fb.group ({roleid: 2}), //SE
      shainNotes: [''],
      shainRegisterDate: [''],
      shainRegisteredBy: [''],
      shainDeletedFlag: false
    })

  }
}

export interface affiliation{
  affiliation_id: number
  active: boolean
  affiliation_desc: string
}
