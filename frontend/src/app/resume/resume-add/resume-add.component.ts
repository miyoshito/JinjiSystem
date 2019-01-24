import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { API_URL } from '../../url-settings'
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { ProfileService } from 'src/app/services/profile.service';
import { Employee } from 'src/app/interfaces/employee';
import { Observable, BehaviorSubject, Subscription, Subject } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { Qualifications, Career, Commendations } from 'src/app/interfaces/resume-details-interface';
import { map, takeUntil, flatMap } from 'rxjs/operators';
import { ResumeService } from 'src/app/services/resume.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { CustomDialogComponent } from 'src/app/resume/custom-dialog/custom-dialog.component';

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

  destroySubject$: Subject<void> = new Subject() // unsubscribe purposes
  displaymsg$: Subject<String> = new Subject<String>() //display message based on clicks
  selectedUser$: Observable<Employee>
  errMsg$: Observable<String>

  title: string
  idsearchbox: boolean
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
    if ((this._router.url).endsWith('/add')){
      this.idsearchbox = true
      this.title = '履歴書登録画面'
    } else {
      this.title = '履歴書編集画面'      
      this.selectedUser$ = this._employeeService.employee$
      //this._employeeService.getShainData(this._route.snapshot.paramMap.get('id'),false,true,false)      
      this.idsearchbox = false
      this.buildEditView(this.selectedUser$)
    }
  }

  saveResume() {
    this.submitted = true
    try {
    this._resumeService.saveResumeAttempt(this.resumeForm.value)
    this._router.navigate(['/soumu/rirekisho/details/'+ this._route.snapshot.paramMap.get('id')])
    alert('更新しました')
    this._employeeService.getShainData(this._route.snapshot.paramMap.get('id'),false,true,false)
    } catch (err) {
      throw err
    }
  }

  buildEditView(withUser: Observable<Employee>){  
    
    this.resetForms()
    withUser.pipe(
      takeUntil(this.destroySubject$),
      map((val, index) =>{        
        this.shainForm.patchValue({ shainId: val.shainId })
        if (!val.resume) {
          this.career.forEach((d: Career) => this.clearCareerRow(d))
          this.qualifications.forEach((d: Qualifications) => this.clearQualificationRow(d))
          this.commendations.forEach((d: Commendations) => this.clearCommendationRow(d))
        } else {
          this.resumeForm.patchValue({
            resumeId: val.resume.resumeId,
            formation: val.resume.formation,
            universityName: val.resume.universityName,
            bunri: val.resume.bunri,
            notes: val.resume.notes
          })
          for (let i of val.resume.careers){            
            if (i.active) this.addCareerRow(i)
          }
          for (let j of val.resume.qualifications){
            if (j.active) this.addQualificationRow(j)
          }
          for (let k of val.resume.commendations){
            if (k.active) this.addCommendationRow(k)
          }
          }
        })).subscribe()
  }    
    
  resetForms() {
    if (this.careerRows.length > 0) {      
      for (let c of this.careerRows.value){
        this.careerRows.removeAt(c)
      }
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
      commendations: this.commendationRows,
      notes: ['']
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
          message: '削除しますよろしいでしょうか？'
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
          message: '削除しますよろしいでしょうか？'
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
          message: '削除しますよろしいでしょうか？'
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
    })
  }
  ngOnDestroy() {
    this.destroySubject$.next()
  }

}






