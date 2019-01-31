import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, Subject, of } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { takeUntil, map } from 'rxjs/operators';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { studyCourse } from 'src/app/interfaces/study-course';

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
    private localeService: BsLocaleService) {
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

  userid: string

  ngOnInit() {
    
    this.buildForm()
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
      this._studyCourseService.insertAttempt(this.studyForm.value).pipe(takeUntil(this.isAlive$),
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
      tuitionFee: [0, Validators.required],
      transportExpenses: [0, Validators.required],
      hotelExpenses: [0, Validators.required],
      overview: [''],
      active: true,
      employee_id: ['']
    })
  }



}
