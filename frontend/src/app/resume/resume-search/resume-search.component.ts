import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SearchForm } from 'src/app/interfaces/resume-details-interface';
import { ResumeService } from 'src/app/services/resume.service';
import { Router } from '@angular/router';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Subject, Observable } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { ProfileService } from 'src/app/services/profile.service';
import { LoginService } from 'src/app/services/login.service';
import { atLeastOne } from 'src/app/validators/atleastOne';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-resume-search',
  templateUrl: './resume-search.component.html',
  styleUrls: ['./resume-search.component.css']
})
export class ResumeSearchComponent implements OnInit {

  searchForm: FormGroup

  active$: Subject<boolean> = new Subject<boolean>()

  searchParam: SearchForm

  params$: Observable<any>

  searchable$: Observable<any>

  age = new Array

  includeRetired:boolean

  displayIncludeBox: boolean

  map:Map<string, string> = new Map<string,string>()


  constructor(private _fb: FormBuilder,
              private _resumeService: ResumeService,
              private _profileService: ProfileService,
              private _router: Router,
              private _employeeService: EmployeeMasterService,
              private _loginService: LoginService,
              private _broadcastService: BroadcastService) { }

  ngOnInit() {
    this.searchable$ = this._resumeService.getSearchableResParams()
    this.params$ = this._employeeService.getViewRendering()
    this.initializeForm()
    this.checkIfSoumu()
  }

  resetForms(){
    this.searchForm.reset()
    this.initializeForm()
  }

  includeRetiredShains(e){
    this.includeRetired = e.target.checked
  }   

  checkIfSoumu(){
    this._broadcastService.userGroup$.pipe(
      takeUntil(this.active$),
      map(groups => {
        if (groups.find(e => e.id == 3)){
          this.displayIncludeBox = true
        }
      })).subscribe()
  }

  
  searchAttempt(){
    this.map.clear()
    Object.keys(this.searchForm.value)
    .filter(f => this.searchForm.value[f] != '')
    .forEach(k => this.map.set(k,this.searchForm.value[k]));

    this.includeRetired == true ? this.map.set('retired', 'true') : this.map.set('retired', 'false');
    this._resumeService.searchResumeAttempt(this.map).pipe(
      takeUntil(this.active$))
      .subscribe(e =>{        
        if (e.status == 500){
        alert('許可されていません')
        return
        }
        else if (!e.body || e.body.length < 1){
        alert('データーが見つかれません')
        return
      }
        else if (e.body.length == 1){
          this._router.navigate(['/soumu/rirekisho/details/'+e.body[0].shainId])
        } else {
        this._resumeService.sendSearchResults(e.body)
        this._router.navigate(['/soumu/rirekisho/results'])
        }
      }, err => {
        return
      })
  }

  initializeForm(){
    this.searchForm = this._fb.group({
      id: [''],
      name: [''],
      kana: [''],
      recruit: [''],
      age: [''],
      study: [''],
      school: [''],
      bunri: [''],
      career: [''],
      qualification: [''],
    }, {validator: atLeastOne(Validators.required)})
  }

  ngOnDestroy(){
    this.active$.next()
  }


}


