import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CurriculumService } from '../curriculum.service';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/profile/profile.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, takeUntil } from 'rxjs/operators';
import { BroadcastService } from 'src/app/broadcast.service';

@Component({
  selector: 'app-curriculum-insert',
  templateUrl: './curriculum-insert.component.html',
  styleUrls: ['./curriculum-insert.component.css']
})
export class CurriculumInsertComponent implements OnInit {

  constructor(private cvService: CurriculumService,
    private _profileService: ProfileService,
    private _fb: FormBuilder,
    private localeService: BsLocaleService,
    private _broadcastService: BroadcastService,
    private _router: Router,
    private _route: ActivatedRoute
  ) {
    localeService.use('ja')
  }

  bsValue: Date = new Date(2017, 7);
  endValue: Date = new Date(2018, 10)
  minMode: BsDatepickerViewMode = 'month';
  bsConfig: Partial<BsDatepickerConfig>;

  userid: string

  isAlive$: Subject<boolean> = new Subject<boolean>();
  submitted: boolean
  cvForm: FormGroup
  indForm: FormGroup
  data$: Observable<any[]>
  loggedUser$: Observable<Employee>

  title: string
  button: string

  authority: string

  typeSelected$: Subject<boolean> = new Subject<boolean>()
  type: number

  industryDropdown$: Observable<any[]>

  ngOnInit() {
    this._broadcastService.userAuthorization$.pipe(
      takeUntil(this.isAlive$),
      map(auth =>{
        this.authority = auth
      })
    ).subscribe()
    this.data$ = this.cvService.getPropertiesList()
    this.industryDropdown$ = this.cvService.getBusinessLogic()

    if ((this._router.url).endsWith('shokumurirekisho/add')) {
      this.title = '職務経歴書登録画面'
      this.button = '登録'
      this.loggedUser$ = this._profileService.cachedUser$
      this.loggedUser$.pipe(
        takeUntil(this.isAlive$),
        map(t => {
          this.userid = t.shainId          
        })).subscribe()
      this.generateForm()
    } else {
      this.generateForm()
      this.title = '職務経歴書編集画面'
      this.button = '更新'
      if (this._route.snapshot.paramMap.get('uid') != null) {
        this.loggedUser$ = this._profileService.getUserProfile(this._route.snapshot.paramMap.get('uid'))
        this.userid = this._route.snapshot.paramMap.get('uid')
      }
      else this.loggedUser$ = this._profileService.cachedUser$
      this.loggedUser$.pipe(
        takeUntil(this.isAlive$),
        map(t => {
          if (this._route.snapshot.paramMap.get('shid') != null) {
            t.curriculum.map((id, index) => {
              if (id.id === parseInt(this._route.snapshot.paramMap.get('shid'))) {
                this.cvForm.patchValue(t.curriculum[index])
                console.log('Start ->' +t.curriculum[index].startdate)
                console.log('End ->' +t.curriculum[index].enddate)
                console.log(this.cvForm.value)
                this.cvForm.patchValue({
                  employee_id: t.shainId,
                  industryType: t.curriculum[index].industryTypeId,
                  assignData: t.curriculum[index].assignData.id
                })
                this.changeChilds(t.curriculum[index].industryTypeId)
                this.cvForm.patchValue({ industryClass: t.curriculum[index].industryClassId })
              }
            })
          }
        })).subscribe()
    }
    this.bsConfig = Object.assign(
      { minMode: this.minMode },
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MMMM' },
      { dateRangeFormat: 'YYYY/MMMM'});
  }


  ngOnDestroy(): void {
  }

  industryForm(): FormGroup {
    return this._fb.group({
      industryid: [],
      classid: [],
    })
  }
  changeChilds(id: number) {
    this.typeSelected$.next(true);
    this.type = id - 1
  }

  insertAttempt() {
    this.submitted = true
    if (this.cvForm.invalid) {
      return
    }
    try {
      this.cvService.insertShokumuAttempt(this.cvForm.value)
      alert('職務経歴書登録完了しました。')
      this.redirect()
    } catch (e) {
      console.log(e)
    }
  }
  redirect() {
    this._broadcastService.userAuthorization$.pipe((takeUntil(this.isAlive$)), map(auth => {
      if (auth === 'ADMIN' || auth === 'SOUMU') {
        this._router.navigate(['admin/shokumurirekisho/details/' + this.userid])
      } else this._router.navigate(['home'])
    })).subscribe()
  }

  get f() { return this.cvForm.controls }

  generateForm() {
    this.cvForm = this._fb.group({
      id: null,
      startdate: ['', Validators.required],
      enddate: ['', Validators.required],
      industryType: ['', Validators.required],
      industryClass: ['', Validators.required],
      targetbusiness: ['', Validators.required],
      customer: ['', Validators.required],
      assignData: ['', Validators.required],
      makerData: [[]],
      osData: [[]],
      dbmsData: [[]],
      responseData: [[]],
      langData: [[]],
      toolsData: [[]],
      employee_id: this.userid
    })
  }
}