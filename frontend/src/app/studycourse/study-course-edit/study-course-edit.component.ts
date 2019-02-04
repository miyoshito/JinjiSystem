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
import { studyCourse } from 'src/app/interfaces/study-course';
import { CurrencyFormatterService } from 'src/app/pipes/currency-formatter.service';

@Component({
  selector: 'app-study-course-edit',
  templateUrl: './study-course-edit.component.html',
  styleUrls: ['./study-course-edit.component.css']
})
export class StudyCourseEditComponent implements OnInit {

  constructor(private _fb: FormBuilder,
    private _studyCourseService: StudycourseService,
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

  isAlive$: Subject<boolean> = new Subject<boolean>()

  selectedUser$: Observable<Employee> = new Observable<Employee>()
  bsConfig: Partial<BsDatepickerConfig>;

  formSubscriber: Subscription

  userid: string

  ngOnInit() {
    this.buildForm()

    this.formSubscriber = this.studyForm.get('tuitionFee').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('tuitionFee').patchValue(this.currFormat.currencyFormat(value))
    })
    this.formSubscriber = this.studyForm.get('transportExpenses').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('transportExpenses').patchValue(this.currFormat.currencyFormat(value))
    })
    this.formSubscriber = this.studyForm.get('hotelExpenses').valueChanges.subscribe(value =>{      
      if (value != this.currFormat.currencyFormat(value))
      this.studyForm.get('hotelExpenses').patchValue(this.currFormat.currencyFormat(value))
    })




    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD' });

    if (this._router.url.endsWith('/edit')) {
      this.title = "教育受講履歴編集画面"
      this.buttonText = '編集'
      if (this._route.snapshot.paramMap.get('uid') == null){
        this._profileService.cachedUser$.pipe(takeUntil(this.isAlive$),map(e => this.userid = e.id)).subscribe()
        this.returnToList = false
      } else {
        this.userid = this._route.snapshot.paramMap.get('uid')
        this.returnToList = true
        this.displayButton = true
      }
      this._employeeService.getShainDatav2(this.userid, "edu").subscribe(e => {
        this.selectedUser$ = of(e.body)
          this.patchData(e.body.educations.find(f => f.id == this._route.snapshot.paramMap.get('scid')))
        this.studyForm.get('employee_id').patchValue(e.body.shainId)
      })
    }

    else if (this._router.url.endsWith('/add')) {
      this.title = "教育受講履歴登録画面"
      this.selectedUser$ = this._employeeService.employee$
      this.buttonText = '登録'
      this.selectedUser$.pipe(takeUntil(this.isAlive$), map(u => this.studyForm.get('employee_id').patchValue(u.shainId))).subscribe()
    }
  }

  insertAttempt() {
    this.submitted = true
    if (!this.studyForm.valid) {
      alert('必須項目が未入力です。')
      return
    } else {

      let scm: studyCourse
      scm = this.studyForm.value
      scm.tuitionFee = this.currFormat.spRemove(this.studyForm.get('tuitionFee').value)
      scm.transportExpenses = this.currFormat.spRemove(this.studyForm.get('transportExpenses').value)
      scm.hotelExpenses = this.currFormat.spRemove(this.studyForm.get('hotelExpenses').value)
      
      this._studyCourseService.insertAttempt(scm).pipe(takeUntil(this.isAlive$),
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
      this._router.navigate(['/public/studycourse/details/' + this._route.snapshot.paramMap.get('uid')])
    else
      this._router.navigate(['/public/studycourse'])
  }

  patchData(e: studyCourse) {
    this.studyForm.patchValue(e)
  }

  buildForm() {
    this.studyForm = this._fb.group({
      id: [''],
      sponsor: [''],
      educationName: [''],
      startPeriod: ['', Validators.required],
      endPeriod: ['', Validators.required],
      venue: [''],
      tuitionFee: [0, {validators: Validators.required, updateOn: 'blur'}],
      transportExpenses: [0, {validators: Validators.required, updateOn: 'blur'}],
      hotelExpenses: [0, {validators: Validators.required, updateOn: 'blur'}],
      overview: [''],
      active: true,
      employee_id: ['']
    })
  }



}
