import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, takeUntil } from 'rxjs/operators';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-curriculum-insert',
  templateUrl: './curriculum-insert.component.html',
  styleUrls: ['./curriculum-insert.component.css']
})
export class CurriculumInsertComponent implements OnInit {

      constructor(private cvService: CurriculumService,
        private _profileService: ProfileService,
        private _fb: FormBuilder,
        private _employeeService: EmployeeMasterService,
        private localeService: BsLocaleService,
        private _broadcastService: BroadcastService,
        private _router: Router,
        private _route: ActivatedRoute
      ) {
        localeService.use('ja')
      }

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

    if ((this._router.url).endsWith('shokumurirekisho/add')) { //profile
      this.title = '職務経歴書登録画面'
      this.button = '登録'
      this._profileService.cachedUser$.pipe(
        takeUntil(this.isAlive$),
        map(e => {
          this._employeeService.getShainData(e.id,true,false,false)
        })
      ).subscribe()
      this.loggedUser$ = this._employeeService.employee$
      this.loggedUser$.pipe(
        takeUntil(this.isAlive$),
        map(t => {
          this.userid = t.shainId          
        })).subscribe()
      this.generateForm()
      } else if (this._router.url.includes('/shokumurirekisho/edit/')) {
        this.generateForm()
        this.button = '更新'
        this.title = '職務経歴書編集画面'
        //verifica se o edit eh de alguem ou meu
        if (this._route.snapshot.paramMap.get('uid') != null) {
          this._employeeService.getShainData(this._route.snapshot.paramMap.get('uid'), true, false, false)
          this.loggedUser$ = this._employeeService.employee$
          this.userid = this._route.snapshot.paramMap.get('uid')
        }
        else {
          this._profileService.cachedUser$.pipe(
            takeUntil(this.isAlive$),
            map(e => {
              this._employeeService.getShainData(e.id,true,false,false)
            })
          ).subscribe()
          this.loggedUser$ = this._employeeService.employee$
        }
        //fim do bloco
        //comeco do map pra saber ql ta editando...
        this.loggedUser$.pipe(
          takeUntil(this.isAlive$),
          map(t => {
            if (this._route.snapshot.paramMap.get('shid') != null) {
              t.curriculum.map((id, index) => {
                if (id.id === parseInt(this._route.snapshot.paramMap.get('shid'))) {
                  this.cvForm.patchValue(t.curriculum[index])                  
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
          // fim do bloco do edit...
      } else { //termina com /id/add
      this.title = '職務経歴書登録画面'
      this.button = '登録'
      this._employeeService.getShainData(this._route.snapshot.paramMap.get('uid'), true)
      this.loggedUser$ = this._employeeService.employee$
      this.userid = this._route.snapshot.paramMap.get('uid')
      this.generateForm()
    }
    this.bsConfig = Object.assign(
      { minMode: this.minMode},      
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM' },
      { dateRangeFormat: 'YYYY/MM' },
      { showWeekNumbers: false}
      );
  }


  ngOnDestroy(): void {
    this.isAlive$.next()
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
      if (auth === 'ADMIN') {
        this._employeeService.getShainData(this.userid, true)
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