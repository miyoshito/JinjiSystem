import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { API_URL } from '../../url-settings'
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { Employee } from 'src/app/interfaces/employee';
import { Observable, BehaviorSubject, Subscription, Subject } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { Qualifications, Career, Commendations } from '../resume-details-interface';
import { map, takeUntil } from 'rxjs/operators';
import { ResumeService } from '../resume.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { CustomDialogComponent } from 'src/app/custom-dialog/custom-dialog.component';

@Component({
  selector: 'app-resume-add',
  templateUrl: './resume-add.component.html',
  styleUrls: ['./resume-add.component.css']
})

export class ResumeAddComponent implements OnInit {

  constructor(private _route: ActivatedRoute,
    private _router: Router,
    private _employeeService: EmployeeMasterService,
    private _profileService: ProfileService,
    private _fb: FormBuilder,
    private _resumeService: ResumeService,
    private _matDialog: MatDialog) { }

  destroySubject$: Subject<void> = new Subject()

  title: string
  displaymsg$: Subject<String> = new Subject<String>()
  selectedUser$: Observable<Employee>
  errMsg$: Observable<String>

  directInsert: boolean
  userselected: boolean
  submitted: boolean

  careerRows: FormArray = this._fb.array([]);
  qualificationRows: FormArray = this._fb.array([]);
  commendationRows: FormArray = this._fb.array([]);

  resumeForm: FormGroup
  careerForm: FormGroup = this._fb.group({ 'careers': this.careerRows });
  qualificationForm: FormGroup = this._fb.group({ 'qualifications': this.qualificationRows });
  commendationForm: FormGroup = this._fb.group({ 'commendations': this.commendationRows });
  shainForm: FormGroup = this._fb.group({ shainId: [''] })



  qualifications: Qualifications[] =
    [{
      qualificationid: null,
      qualification_year: null,
      qualification_month: null,
      qualification_name: '',
      qualification_result: '',
      active: null
    }]

  career: Career[] = [{
    careerid: null,
    career_year: null,
    career_month: null,
    career_scwk: '',
    career_dpaf: '',
    career_result: '',
    active: null
  }]

  commendations: Commendations[] = [{
    commendationid: null,
    commendation_year: null,
    commendation_month: null,
    commendation_name: '',
    commendation_result: '',
    active: null
  }]



  ngOnInit() {
    this.buildResumeForm()
    this._route.parent.parent.url.pipe(takeUntil(this.destroySubject$)).subscribe(url => {
      console.log(url[0].path)
      if (url[0].path === 'admin') {
        //since you are trying to direct insert a new rirekisho, you need to pick a employee.
        this.title = '履歴書登録画面'
        this.directInsert = true
      }
    })
  }

  saveResume() {
    this.submitted = true
    this._resumeService.saveResumeAttempt(this.resumeForm.value).pipe(
      takeUntil(this.destroySubject$))
      .subscribe(res => {
        console.log()
        if (res.status === 200) {
          this._router.navigate(['home'])
        } else {
          this.displaymsg$.next('Something bad happened, please try again or contact a administrator.')
        }
      })
  }

  selectEmployee(shainid: string) {
    this.resetForms()
    console.log(shainid)
    if (shainid === '' || shainid === null || shainid === undefined) {
      this._router.navigate(['admin/employee-list'])
    } else {
      this.selectedUser$ = this._profileService.getUserProfile(shainid)
      this.selectedUser$.pipe(
        takeUntil(this.destroySubject$),
        map((data) => {
          if (data === null) {
            this.displaymsg$.next('id not found, redirecting to list...')
            setTimeout(() => {
              this._router.navigate(['admin/employee-list'])
            }, 3000)
          } else {
            this.shainForm.patchValue({ shainId: shainid })
            if (!data.resume) {
              console.log('!!!!!!!!!!!!!!!!!!')
              this.career.forEach((d: Career) => this.clearCareerRow(d))
              this.qualifications.forEach((d: Qualifications) => this.clearQualificationRow(d))
              this.commendations.forEach((d: Commendations) => this.clearCommendationRow(d))
            } else {
              this.resumeForm.patchValue({
                resumeId: data.resume.resumeId,
                formation: data.resume.formation,
                universityName: data.resume.universityName,
                bunri: data.resume.bunri
              })
              data.resume.careers.forEach((car) => { 
                //temporary until i finish the custom JPA Entity
                (car.active) ? this.addCareerRow(car) : null
               })
              data.resume.qualifications.forEach((qua) => { 
                (qua.active) ? this.addQualificationRow(qua) : null
               })
              data.resume.commendations.forEach((com) => { 
              (com.active) ? this.addCommendationRow(com) : null
               })
            }
          }
        })).subscribe()
    }
  }

  resetForms() {
    if (this.careerRows.length > 0) {
      this.careerRows.value.forEach(value => {
        this.careerRows.removeAt(value)
      });
    }

    if (this.qualificationRows.length > 0) {
      this.qualificationRows.value.forEach(value => {
        this.qualificationRows.removeAt(value)
      });
    }

    if (this.commendationRows.length > 0) {
      this.commendationRows.value.forEach(value => {
        this.commendationRows.removeAt(value)
      });
    }

    this.resumeForm.reset()
    this.shainForm.reset()

  }

  buildResumeForm() {
    this.resumeForm = this._fb.group({
      resumeId: null,
      formation: [''],
      universityName: [''],
      bunri: [''],
      employee: this.shainForm,
      careers: this.careerRows,
      qualifications: this.qualificationRows,
      commendations: this.commendationRows
    })
  }

  clearCareerRow(d?: Career) {
    const row = this._fb.group({
      careerid: null,
      career_year: null,
      career_month: null,
      career_scwk: '',
      career_dpaf: '',
      career_result: '',
      active: null
    })
    this.careerRows.push(row)
  }

  addCareerRow(d?: Career) {
    const row = this._fb.group({
      careerid: d.careerid,
      career_year: d.career_year,
      career_month: d.career_month,
      career_scwk: d.career_scwk,
      career_dpaf: d.career_dpaf,
      career_result: d.career_result,
      active: d.active
    })
    this.careerRows.push(row)
  }

  clearQualificationRow(d: Qualifications) {
    const row = this._fb.group({
      qualificationid: null,
      qualification_year: null,
      qualification_month: null,
      qualification_name: null,
      qualification_result: null,
      active: null
    })
    this.qualificationRows.push(row)
  }

  addQualificationRow(d: Qualifications) {
    const row = this._fb.group({
      qualificationid: d.qualificationid,
      qualification_year: d.qualification_year,
      qualification_month: d.qualification_month,
      qualification_name: d.qualification_name,
      qualification_result: d.qualification_result,
      active: d.active
    })
    this.qualificationRows.push(row)
  }

  clearCommendationRow(d: Commendations) {
    const row = this._fb.group({
      commendationid: null,
      commendation_year: null,
      commendation_month: null,
      commendation_name: null,
      commendation_result: null,
      active: null
    })
    this.commendationRows.push(row)
  }

  addCommendationRow(d: Commendations) {
    const row = this._fb.group({
      commendationid: d.commendationid,
      commendation_year: d.commendation_year,
      commendation_month: d.commendation_month,
      commendation_name: d.commendation_name,
      commendation_result: d.commendation_result,
      active: d.active
    })
    this.commendationRows.push(row)

  }

  async removeCareerRow(index: number) {
    let t: Career = this.careerRows.at(index).value

    if (t.careerid == null) this.careerRows.removeAt(index)

    else {
      const dialogRef = this._matDialog.open(CustomDialogComponent, {
        data: {
          title: '経歴削除確認画面',
          message: 'You are trying to delete a already saved information, dou you REALLY wanna do dis?'
        },
        width: '350px',
        height: '400px',
      })
      await dialogRef.afterClosed().subscribe(res => {
        if (res){
        this.softDelete('career', t.careerid)
        this.careerRows.removeAt(index)
        }
      })
    }
  }

  async removeQualificationRow(index: number) {
    let q: Qualifications = this.qualificationRows.at(index).value

    if (q.qualificationid == null) this.qualificationRows.removeAt(index)
    else {
      const dialogRef = this._matDialog.open(CustomDialogComponent, {
        data: {
          title: '資格削除確認画面',
          message: 'You are trying to delete a already saved information, dou you REALLY wanna do dis?'
        },
        width: '350px',
        height: '400px',
      })
      await dialogRef.afterClosed().subscribe(res => {
        if (res){
        this.softDelete('qualification', q.qualificationid)
        this.qualificationRows.removeAt(index)
        }
      })
    }
  }
  async removeCommendationRow(index: number) {
    let c: Commendations = this.commendationRows.at(index).value

    if(c.commendationid == null) this.commendationRows.removeAt(index);
    else{
      const dialogRef = this._matDialog.open(CustomDialogComponent, {
        data: {
          title: '資格削除確認画面',
          message: 'You are trying to delete a already saved information, dou you REALLY wanna do dis?'
        },
        width: '350px',
        height: '400px',
      })
      await dialogRef.afterClosed().subscribe(res => {
        if (res){
        this.softDelete('commendation', c.commendationid)
        this.commendationRows.removeAt(index)
        }
      })

    }
  }



  softDelete(type: string, id: number) {
    this._resumeService.softDeleteDetail(type, id).pipe(
      takeUntil(this.destroySubject$)
    ).subscribe(res =>{
      console.log(res)
    })
  }
  ngOnDestroy() {
    this.destroySubject$.next()
  }

}






