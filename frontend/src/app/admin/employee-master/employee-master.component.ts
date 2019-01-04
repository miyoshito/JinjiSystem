import { Component, OnInit, ViewEncapsulation, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EmployeeMasterService } from './employee-master.service';
import { Observable, Subscription, Subject, of } from 'rxjs';
import { map, tap, takeUntil } from 'rxjs/operators';
import { Data } from 'src/app/interfaces/data';
import { Employee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/broadcast.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/login/login.service';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';

@Component({
  selector: 'app-employee-master',
  templateUrl: './employee-master.component.html',
  styleUrls: ['./employee-master.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EmployeeMasterComponent implements OnInit {

  temp: Subscription
  displayInsertButtons: boolean

  iid: string = ''

  employeeForm: FormGroup
  
  sex: string[] = ['女','男'];
  bloodType: String[] = ['A+型','B+型','O+型','AB+型','A-型','B-型','O-型','AB-型']

  submitted: boolean
  title: string
  buttonLabel: string
  myPosition: string
  isInserting: boolean
  isEditing: boolean
  isProfile: boolean

  currentRoute: string
  passwordbutton: boolean
  
  role$: Observable<any>
  isLoggedIn$: Observable<boolean>  
  params$: Observable<any[]>
  unsub$: Subject<boolean> = new Subject<boolean>()

  yakushokuSelects: Data[]

  selectedUser$: Observable<Employee>

  bsValue: String = new Date().toISOString().slice(0,10);
  endValue: Date = new Date()
  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial<BsDatepickerConfig>;

  constructor(private _fb: FormBuilder,
              private _employeeService: EmployeeMasterService,
              private _broadcastService: BroadcastService,
              private _profileService: ProfileService,
              private _route: ActivatedRoute,
              private _router: Router,
              private _loginService: LoginService,
              private localeService: BsLocaleService,) { 
                localeService.use('ja')
                this.passwordbutton = false
                this.isInserting = false
                this.isEditing = false
                this.isProfile = false
                this.displayInsertButtons = false
              }

  ngOnDestroy(){
    this.unsub$.next()
  }

  ngOnInit() {
  this.initializeForm()
  this.params$ = this._employeeService.getViewRendering()
  this.isLoggedIn$ = this._broadcastService.userAuthenticated$
  this.bsConfig = Object.assign(
    { minMode: this.minMode },
    { containerClass: "theme-red" },
    { dateInputFormat: 'YYYY/MMMM/dd' },
    { dateRangeFormat: 'YYYY/MMMM/dd'});
  
  if ((this._router.url).endsWith('/edit')){    
      this.selectedUser$ = this._employeeService.employee$
      this._employeeService.getShainData(this._route.snapshot.paramMap.get('id'))      
      this.loadUserData()
      this.title = '社員マスタ編集画面'
      this.buttonLabel = '更新'
      this.displayInsertButtons = true
      this.isEditing = true
      this.passwordbutton = true
  } else if ((this._router.url).endsWith('/profile')) {
    this.selectedUser$ = this._employeeService.employee$
      this._profileService.cachedUser$.pipe(
        takeUntil(this.unsub$),
        map(e => {
          this._employeeService.getShainData(e.id)
        })
      ).subscribe();
      this.title = 'プロフィール画面'
      this.isProfile = true
      this.buttonLabel = '更新'
      this.passwordbutton = true
      this.loadUserData()
  } else {
      this.title = '社員マスタ登録画面'
      this.isInserting  = true
      this.buttonLabel = '登録'
    }
  }

  onChange(event){    
    this._employeeService.checkIfShainExists(event.target.value).pipe(
      takeUntil(this.unsub$),
      map(r =>{
    if(r) {
    console.log(r)
    this.selectedUser$ = this._employeeService.employee$
    this.loadUserData();
    this.displayInsertButtons = true
    this.buttonLabel = '更新'
    this.passwordbutton = true
    }
    })).subscribe()
  }

  returnToSearch(){
    this._router.navigate(['/admin/employee-search'])
  }

  loadUserData(){
    this.selectedUser$.pipe(takeUntil(this.unsub$))
    .subscribe(data =>{
      console.log(data.position)
      this.myPosition = data.position.desc
      this.employeeForm.patchValue(data)      
      data.shainJoinedDate != null ? this.employeeForm.patchValue({shainJoinedDate: data.shainJoinedDate.slice(0,10)}) : null
      data.shainBirthday != null ? this.employeeForm.patchValue({shainBirthday: data.shainBirthday.slice(0,10)}) : null
      data.shainRegisterDate != null ? this.employeeForm.patchValue({shainRegisterDate: data.shainRegisterDate.slice(0,10)}) : null
      data.shainRetiredDate != null ? this.employeeForm.patchValue({shainRetiredDate: data.shainRetiredDate.slice(0,10)}) : null
    })
    this.iid = this.employeeForm.controls.shainId.value
  }

  redirect(to: string){
    if(this.iid === '')
    this.iid = this._route.snapshot.paramMap.get('id')
    this._router.navigate(['/admin/'+to+'/details/'+this.iid])
  }
  
  show: boolean
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

      if ((this._router.url).endsWith('/edit')){
        alert('ユーザー更新されました。')
          this._router.navigate(['admin/employee-search'])
      } else if ((this._router.url).endsWith('/profile')) {
        alert('アップデート完了しました。')
        this._profileService.getLoggedInUserData()
        this._router.navigate(['home'])        
      } else {
        alert('ユーザーが挿入されました。')
        this._router.navigate(['admin/employee-search'])
      }
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
    this.passwordbutton = false
    this.isInserting = true
    this.isEditing = false
    this.buttonLabel = '登録'
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
      shainRetired: false,
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
