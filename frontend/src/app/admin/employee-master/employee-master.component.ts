import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EmployeeMasterService } from './employee-master.service';
import { Observable, Subscription } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Data } from 'src/app/interfaces/data';
import { Employee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/broadcast.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/login/login.service';

@Component({
  selector: 'app-employee-master',
  templateUrl: './employee-master.component.html',
  styleUrls: ['./employee-master.component.css']
})
export class EmployeeMasterComponent implements OnInit {

  employeeForm: FormGroup

  
  sex: string[] = ['女','男'];
  bloodType: String[] = ['A+型','B+型','O+型','AB+型','A-型','B-型','O-型','AB-型']


  submitted: boolean
  title: string
  success$: boolean
  sub: Subscription
  successMsg: string
  show: boolean
  
  role$: Observable<any>
  response$: Observable<any>
  isLoggedIn$: Observable<boolean>  
  loggedUser$: Observable<any>
  params$: Observable<any[]>
  


  constructor(private _fb: FormBuilder,
              private _employeeService: EmployeeMasterService,
              private _broadcastService: BroadcastService,
              private _profileService: ProfileService,
              private _route: ActivatedRoute,
              private _router: Router,
              private _loginService: LoginService) { 
                this.success$ = false
              }

  ngOnInit() {
  this.initializeForm()
  this.params$ = this._employeeService.getViewRendering()
  this.isLoggedIn$ = this._broadcastService.userAuthenticated$

  console.log('u are navigating from ' +this._route.parent)
  }

  ngAfterContentInit(){
    
  this._route.parent.url.subscribe(url =>{
    console.log(url[0].path)
    if (url[0].path === 'profile') { // it means im seeing my profile!
      this.title = 'プロフィール画面'
      this.loadUserData()
    } else if (url[0].path === 'admin') { //it means im trying to add a new profile
      this.title = '社員マスタ登録画面'
    } else {
      this.title = '社員マスタ編集画面' // it means theyre editing or smth
    }
  })
  }

  ngOnDestroy(){
    this.success$ = false
  }

  loadUserData(){
    this._profileService.cachedUser$.subscribe(data =>{
      this.employeeForm.patchValue(data)
      data.shainJoinedDate != null ? this.employeeForm.patchValue({shainJoinedDate: data.shainJoinedDate.slice(0,10)}) : console.log('')
      data.shainBirthday != null ? this.employeeForm.patchValue({shainBirthday: data.shainBirthday.slice(0,10)}) : console.log('')
      data.shainRegisterDate != null ? this.employeeForm.patchValue({shainRegisterDate: data.shainRegisterDate.slice(0,10)}) : console.log('')
      data.shainRetiredDate != null ? this.employeeForm.patchValue({shainRetiredDate: data.shainRetiredDate.slice(0,10)}) : console.log('')      
    })
  }

  showPassword() {
    this.show = !this.show;
  }

  submitForm(){
    let employee: Employee = this.employeeForm.value
    if (this.employeeForm.invalid) {
      console.log('invalidr...')
      return;
    }
    
    try {
      this._employeeService.insertShainAttempt(employee)
      this.success$ = true
      this._router.navigate(['home'])
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
