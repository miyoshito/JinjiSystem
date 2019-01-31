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
import { QualificationsService } from 'src/app/services/qualifications.service';
import { Qualifications } from 'src/app/interfaces/qualifications';


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
    private localeService: BsLocaleService) {
    localeService.use('ja')
  }

  studyForm: FormGroup

  title: String

  submitted: boolean
  buttonText: string
  displayButton: boolean
  returnToList: boolean

  userid: string

  isAlive$: Subject<boolean> = new Subject<boolean>()

  selectedUser$: Observable<Employee> = new Observable<Employee>()
  bsConfig: Partial<BsDatepickerConfig>;

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
          this.displayButton = true
          this.returnToList = true          
        }
        this._employeeService.getShainDatav2(this.userid, "qua").subscribe(e => {
          this.selectedUser$ = of(e.body)
            this.patchData(e.body.qualifications.find(f => f.id == this._route.snapshot.paramMap.get('scid')))
          this.studyForm.get('employee_id').patchValue(e.body.shainId)
        })
      }

      // if (this._router.url.endsWith('/edit')) {
      //   this.title = "資格情報歴編集画面"
      //   this.buttonText = '編集'        
      //   if (this._route.snapshot.paramMap.get('uid') == null){
      //     this._profileService.cachedUser$.pipe(takeUntil(this.isAlive$),map(e => this.userid = e.id)).subscribe()
      //     this.returnToList = false
      //   } else {
      //     this.userid = this._route.snapshot.paramMap.get('uid')
      //     this.returnToList = true
      //   }
      //   this._employeeService.getShainDatav2(this.userid, "qua").subscribe(e => {
      //     this.selectedUser$ = of(e.body)
      //       this.patchData(e.body.qualifications.find(f => f.id == this._route.snapshot.paramMap.get('scid')))
      //     this.studyForm.get('employee_id').patchValue(e.body.shainId)
      //   })
      // }
      
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
      this._qualificationService.insertAttempt(this.studyForm.value).pipe(takeUntil(this.isAlive$),
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
      examFee: [0, Validators.required],
      extraFee: [0, Validators.required],
      coveredFee: [0, Validators.required],
      reward: [0, Validators.required],
      active: true,
      employee_id: ['']
    })
  }



}
