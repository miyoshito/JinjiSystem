import { Component, OnInit, ViewEncapsulation } from '@angular/core';
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
  styleUrls: ['./employee-master.component.css'],
  encapsulation: ViewEncapsulation.None
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
  currentRoute: string
  passwordbutton: boolean
  
  role$: Observable<any>
  response$: Observable<any>
  isLoggedIn$: Observable<boolean>  
  loggedUser$: Observable<any>
  params$: Observable<any[]>

  subscription: Subscription
  


  constructor(private _fb: FormBuilder,
              private _employeeService: EmployeeMasterService,
              private _broadcastService: BroadcastService,
              private _profileService: ProfileService,
              private _route: ActivatedRoute,
              private _router: Router,
              private _loginService: LoginService) { 
                this.success$ = false
                this.passwordbutton = false
              }

  ngOnInit() {
  this.initializeForm()
  this.params$ = this._employeeService.getViewRendering()
  this.isLoggedIn$ = this._broadcastService.userAuthenticated$
  }

  ngAfterContentInit(){    
  this.subscription = this._route.parent.url.subscribe(url =>{
    this.currentRoute = url[0].path
    if (url[0].path === 'profile') { // it means im seeing my profile!
      this.title = 'プロフィール画面'
      this.passwordbutton = true
      this.loadUserData()
    } else if (url[0].path === 'admin') { //it means im trying to add a new profile
      this.title = '社員マスタ登録画面'
    } else {
      this.title = '社員マスタ編集画面' // it means theyre editing or smth
    }
  })
  }

  ngOnDestroy(){
    this.subscription.unsubscribe()
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
  changePassword(){
    alert('開発中です')
  }

  submitForm(){
    let employee: Employee = this.employeeForm.value
    if (this.employeeForm.invalid) {
      this.submitted = true
      return;
    }

    try {
      this._employeeService.insertShainAttempt(employee)
      this.success$ = true
      
      if (this.currentRoute === 'profile'){
        this._router.navigate(['home'])
        alert('アップデート完了しました。')
      } else alert('ユーザーが挿入されました。')
      this.resetForms()
      } catch (err) {
        throw err
  }
}
  resetForms(){
    this.employeeForm.reset()
    this.positionForm.reset()
    this.areaForm.reset()
    this.carForm.reset()

    this.initializeForm()
  }

  get f() { return this.employeeForm.controls }
  get p() { return this.positionForm.controls }
  get a() {return this.areaForm.controls}
  get car() {return this.carForm.controls}
  
  positionForm: FormGroup = this._fb.group({id: ['',Validators.required]})
  areaForm: FormGroup = this._fb.group({id: ['',Validators.required]})
  carForm: FormGroup = this._fb.group({id: [1,Validators.required]})
  
  initializeForm(){
    this.employeeForm = this._fb.group({
      shainId: [''],
      shainPassword: [''],
      shainName: ['', Validators.required],
      shainRecruit: ['', Validators.required],
      shainKana: ['', Validators.required],
      shainBirthday: ['', Validators.required],
      shainBloodType: [''],
      shainSex: ['', Validators.required],
      position: this.positionForm,
      affiliation: ['', Validators.required],
      shainSupport: [false],
      shainMarried: [false],
      shainHomePhoneNumber: [''],
      shainMobilePhoneNumber: [''],
      shainMail: [''],
      shainMobileMail: [''],
      shainPostalCode: [''],
      shainAddress: [''],
      shainArea: this.areaForm,
      shainJoinedDate: ['', Validators.required],
      shainRetiredDate: [''],
      shainActive: true,
      shainCarModel: this.carForm,
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
