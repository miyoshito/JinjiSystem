import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { API_URL } from '../../url-settings'
import { Router, ActivatedRoute } from '@angular/router';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { Employee } from 'src/app/interfaces/employee';
import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { Qualifications, Career, Commendations } from '../resume-details-interface';
import { map } from 'rxjs/operators';

const url = API_URL

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
    private _fb: FormBuilder) { }

  subs: Subscription
  title: string
  displaymsg: string = ''
  selectedUser$: Observable<Employee>

  directInsert: boolean
  userselected: boolean

  //public uploader: FileUploader = new FileUploader({ url: url });
  dataSource = new BehaviorSubject<AbstractControl[]>([]);
  qualifications: Qualifications[] =
    [{
      qualification_id: null,
      qualification_year: null,
      qualification_month: null,
      qualification_name: '',
      qualification_result: ''
    }]

  career: Career[] = [{
    career_id: null,
    career_year: null,
    career_month: null,
    career_scwk: '',
    career_dpaf: '',
    career_result: ''
  }]

  commendations: Commendations[] = [{
    commendation_id: null,
    commendation_year: null,
    commendation_month: null,
    commendation_name: '',
    commendation_result: ''
  }]

  displayedColumnsA = ['年', '月', '学校/職場', '学科/所属', '結果']
  displayedColumnsB = ['年', '月', '資格名', '結果']
  displayedColumnsC = ['年', '月', '内容', '結果']

  careerRows: FormArray = this._fb.array([]);
  qualificationRows: FormArray = this._fb.array([]);
  commendationRows: FormArray = this._fb.array([]);
  careerForm: FormGroup = this._fb.group({ 'careers': this.careerRows });
  qualificationForm: FormGroup = this._fb.group({ 'qualifications': this.qualificationRows });
  commendationForm: FormGroup = this._fb.group({ 'commendations': this.commendationRows });

  ngOnInit() {
    this._route.parent.parent.url.subscribe(url => {
      console.log(url[0].path)
      if (url[0].path === 'admin') {
        //since you are trying to direct insert a new rirekisho, you need to pick a employee.
        this.title = '履歴書登録画面'
        this.directInsert = true
      }
    })
  }

  selectEmployee(shainid: string) {
    console.log(shainid)
    if (shainid == '' || shainid === null || shainid === undefined) {
      this._router.navigate(['admin/employee-list'])
    } else {
      this.selectedUser$ = this._profileService.getUserProfile(shainid)

      this.subs = this.selectedUser$.pipe(
        map((data) => {
          if (data === null) {
            this.displaymsg = 'id not found, redirecting to list...'
            setTimeout(() => {
              this._router.navigate(['admin/employee-list'])
            }, 3000)
          } else {
            if (!data.resume) {
              console.log('!!!!!!!!!!!!!!!!!!')
              this.career.forEach((d: Career) => this.clearCareerRow(d))              
              this.qualifications.forEach((d: Qualifications) => this.clearQualificationRow(d))
              this.commendations.forEach((d: Commendations) => this.clearCommendationRow(d))
            } else {
              data.resume.careers.forEach((car) => { this.addCareerRow(car) })
              data.resume.qualifications.forEach((qua) => { this.addQualificationRow(qua) })
              data.resume.commendations.forEach((com) => { this.addCommendationRow(com) })
            }
          }
        })).subscribe()
    }
  }

  clearCareerRow(d?: Career){
    const row = this._fb.group({
      career_id:  null,
      career_year: null,
      'career_month': null,
      'career_scwk':'',
      'career_dpaf':'',
      'career_result': ''
    })
    this.careerRows.push(row)
    this.updateView('career');
  }

  addCareerRow(d?: Career) {    
    const row = this._fb.group({
      career_id:  d.career_id,
      career_year: d.career_year,
      'career_month': d.career_month,
      'career_scwk': d.career_scwk,
      'career_dpaf': d.career_dpaf,
      'career_result': d.career_result
    })
    this.careerRows.push(row)
    this.updateView('career');
  }

  clearQualificationRow(d: Qualifications) {
    const row = this._fb.group({
      'qualification_id': null,
      'qualification_year': null,
      'qualification_month': null,
      'qualification_name': null,
      'qualification_result': null
    })
    this.qualificationRows.push(row)
    this.updateView('qualifications');
  }

  addQualificationRow(d: Qualifications) {
    const row = this._fb.group({
      'qualification_id': d.qualification_id,
      'qualification_year': d.qualification_year,
      'qualification_month': d.qualification_month,
      'qualification_name': d.qualification_name,
      'qualification_result': d.qualification_result
    })
    this.qualificationRows.push(row)
    this.updateView('qualifications');
  }

  clearCommendationRow(d: Commendations) {
    const row = this._fb.group({
      commendation_id: null,
      commendation_year: null,
      commendation_month: null,
      commendation_name: null,
      commendation_result: null,
    })
    this.commendationRows.push(row)
    this.updateView('commendations');
  }

  addCommendationRow(d: Commendations) {
    const row = this._fb.group({
      commendation_id: d.commendation_id,
      commendation_year: d.commendation_year,
      commendation_month: d.commendation_month,
      commendation_name: d.commendation_name,
      commendation_result: d.commendation_result
    })
    this.commendationRows.push(row)
    this.updateView('commendations');
  }

  updateView(type: string) {
    type === 'career' ? this.dataSource.next(this.careerRows.controls) :
      type === 'qualifications' ? this.dataSource.next(this.qualificationRows.controls) :
        this.dataSource.next(this.commendationRows.controls)
  }

  ngOnDestroy() {
    if (this.subs) {
      this.subs.unsubscribe()
    }
  }

}






