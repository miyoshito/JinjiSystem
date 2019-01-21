import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudycourseService } from 'src/app/services/studycourse.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/services/profile.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { takeUntil, map } from 'rxjs/operators';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';

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

  isAlive$: Subject<boolean> = new Subject<boolean>()

  selectedUser$: Observable<Employee>
  bsConfig: Partial<BsDatepickerConfig>;

  ngOnInit() {
    this.buildForm()
    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD'});

    if (this._router.url.endsWith('/edit')){
    this.title = "教育受講履歴編集画面"
    this.buttonText = '編集'
    this.selectedUser$ = this._employeeService.employee$
    this.patchData(this.selectedUser$)
    }
    else if(this._router.url.endsWith('/add')){
    this.title = "教育受講履歴登録画面"
    this.buttonText = '登録'
      this.selectedUser$ = this._employeeService.employee$
      this.selectedUser$.pipe(takeUntil(this.isAlive$),map(u => this.studyForm.get('employee_id').patchValue(u.shainId))).subscribe()
    }
  }

  insertAttempt(){
    this.submitted = true
    if(!this.studyForm.valid){
      return      
    } else {
      this._studyCourseService.insertAttempt(this.studyForm.value).pipe(takeUntil(this.isAlive$),
      map(res =>{
        if (res.status === 200){
          alert(this.buttonText+'しました')
          this.redirect()
        }
      })).subscribe()
    }
    
  }

  ngOnDestroy(): void {
    this.isAlive$.next()
  }

  get f(){return this.studyForm.controls}

  redirect(){
    if(this._router.url.includes(this._route.snapshot.paramMap.get('uid')))
      this._router.navigate(['/public/studycourse/details/'+this._route.snapshot.paramMap.get('uid')])    
      else
      this._router.navigate(['/public/studycourse'])
    
  }

  patchData(e: Observable<Employee>){
    e.pipe(takeUntil(this.isAlive$),
    map(em =>{
      this.studyForm.patchValue(em.educations.find(id => this._route.snapshot.paramMap.get('scid') == id.id))
      this.studyForm.get('employee_id').patchValue(em.shainId)
    })).subscribe()
  }

  buildForm(){
    this.studyForm = this._fb.group({
      id: [''],
      sponsor: [''],
      educationName: [''],
      startPeriod: [''],
      endPeriod: [''],
      venue: [''],
      tuitionFee: [0, Validators.required],
      transportExpenses: [0, Validators.required],
      hotelExpenses: [0, Validators.required],
      overview: [''],
      active: true,
      updated: [''],
      updatedBy: [''],
      employee_id: ['']
    })
  }



}
