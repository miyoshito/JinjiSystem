import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, Subject, of, Subscription } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { takeUntil, map } from 'rxjs/operators';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { QualificationsService } from 'src/app/services/qualifications.service';
import { Qualifications } from 'src/app/interfaces/qualifications';
import { formatCurrency } from '@angular/common';
import { CurrencyFormatterService } from 'src/app/pipes/currency-formatter.service';


@Component({
  selector: 'app-qualifications-add',
  templateUrl: './qualifications-add.component.html',
  styleUrls: ['./qualifications-add.component.css']
})
export class QualificationsAddComponent implements OnInit {

  constructor(private _fb: FormBuilder,
    private _qualificationService: QualificationsService,
    private _router: Router,
    private _route: ActivatedRoute,
    private _profileService: ProfileService,
    private _employeeService: EmployeeMasterService,
    private localeService: BsLocaleService,
    private currFormat: CurrencyFormatterService) {
    localeService.use('ja')
  }

  studyForm: FormGroup

  title: String

  submitted: boolean
  buttonText: string
  displayButton: boolean
  returnToList: boolean

  userid: string

  testevalue: string

  isAlive$: Subject<boolean> = new Subject<boolean>()

  selectedUser$: Observable<Employee> = new Observable<Employee>()
  bsConfig: Partial<BsDatepickerConfig>;

  formSubscriber: Subscription

  ngOnInit() {
    this.buildForm()

    this.formSubscriber = this.studyForm.get('examFee').valueChanges.subscribe(value =>{      
        if (value != this.currFormat.currencyFormat(value))
        this.studyForm.get('examFee').patchValue(this.currFormat.currencyFormat(value))
    })

    this.formSubscriber = this.studyForm.get('extraFee').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('extraFee').patchValue(this.currFormat.currencyFormat(value))
    })

    this.formSubscriber = this.studyForm.get('coveredFee').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('coveredFee').patchValue(this.currFormat.currencyFormat(value))
    })

    this.formSubscriber = this.studyForm.get('reward').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('reward').patchValue(this.currFormat.currencyFormat(value))
    })
    
    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD' });

      if (this._router.url.endsWith('/edit')) {
        this.title = "資格情報編集画面"
        this.buttonText = '編集'
        if (this._route.snapshot.paramMap.get('uid') == null){
          this._profileService.cachedUser$.pipe(takeUntil(this.isAlive$),map(e => this.userid = e.id)).subscribe()
          this.returnToList = false
        } else {
          this.userid = this._route.snapshot.paramMap.get('uid')
          this.displayButton = true
          this.returnToList = true          
        }
        this._employeeService.getShainDatav2(this.userid, "qua").subscribe(e => {
          this.selectedUser$ = of(e.body)
            this.patchData(e.body.qualifications.find(f => f.id == this._route.snapshot.paramMap.get('scid')))
          this.studyForm.get('employee_id').patchValue(e.body.shainId)
        })
      }
      
    else if (this._router.url.endsWith('/add')) {
      this.title = "資格情報登録画面"
      this.buttonText = '登録'
      this.selectedUser$ = this._employeeService.employee$
      this.selectedUser$.pipe(takeUntil(this.isAlive$), map(u => this.studyForm.get('employee_id').patchValue(u.shainId))).subscribe()
    }

    
  }

  insertAttempt() {
    this.submitted = true
    if (!this.studyForm.valid) {
      alert('必須項目が未入力です。')
      return
    } else {

      let qua: Qualifications
      qua = this.studyForm.value
      qua.examFee = this.currFormat.spRemove(this.studyForm.get('examFee').value)
      qua.extraFee = this.currFormat.spRemove(this.studyForm.get('extraFee').value)
      qua.coveredFee = this.currFormat.spRemove(this.studyForm.get('coveredFee').value)
      qua.reward = this.currFormat.spRemove(this.studyForm.get('reward').value)

      console.log(qua)

      this._qualificationService.insertAttempt(qua).pipe(takeUntil(this.isAlive$),


        map(res => {
          if (res.status === 200) {
            alert(this.buttonText + 'しました')
            this.redirect()
          }
        })).subscribe()
    }

  }
  ngOnDestroy(): void {
    this.isAlive$.next()
    this.formSubscriber.unsubscribe()
  }

  get f() { return this.studyForm.controls }

  redirect() {
    if (this._router.url.includes(this._route.snapshot.paramMap.get('uid')))
      this._router.navigate(['/public/qualifications/details/' + this._route.snapshot.paramMap.get('uid')])
    else
      this._router.navigate(['/public/qualifications'])
  }

  patchData(e: Qualifications) {
   this.studyForm.patchValue(e)
  }


  buildForm() {
    this.studyForm = this._fb.group({
      id: [''],
      sponsor: [''],
      qName: [''],
      examDate: ['', Validators.required],
      examPlace: [''],
      results: [''],
      examFee: [0, {validators: Validators.required, updateOn: 'blur'}],
      extraFee: [0, {validators: Validators.required, updateOn: 'blur'}],
      coveredFee: [0, {validators: Validators.required, updateOn: 'blur'}],
      reward: [0, {validators: Validators.required, updateOn: 'blur'}],
      active: true,
      employee_id: ['']
    })
  }



}
