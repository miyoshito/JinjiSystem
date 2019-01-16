import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SearchForm } from 'src/app/interfaces/resume-details-interface';
import { ResumeService } from 'src/app/services/resume.service';
import { Router } from '@angular/router';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Subject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { ProfileService } from 'src/app/services/profile.service';
import { LoginService } from 'src/app/services/login.service';
import { atLeastOne } from 'src/app/validators/atleastOne';

@Component({
  selector: 'app-resume-search',
  templateUrl: './resume-search.component.html',
  styleUrls: ['./resume-search.component.css']
})
export class ResumeSearchComponent implements OnInit {

  searchForm: FormGroup

  active$: Subject<boolean> = new Subject<boolean>()

  searchParam: SearchForm

  age = new Array

  map:Map<string, string> = new Map<string,string>()


  constructor(private _fb: FormBuilder,
              private _resumeService: ResumeService,
              private _profileService: ProfileService,
              private _router: Router,
              private _loginService: LoginService) { }

  ngOnInit() {
    this.initializeForm()
  }

  resetForms(){
    this.searchForm.reset()
    this.initializeForm()
  }

  
  searchAttempt(){
    this.map.clear()
    Object.keys(this.searchForm.value)
    .filter(f => this.searchForm.value[f] != '')
    .forEach(k => this.map.set(k,this.searchForm.value[k]))
    
    this._resumeService.searchResumeAttempt(this.map).pipe(
      takeUntil(this.active$))
      .subscribe(e =>{
        if (e.status == 500){
        alert('許可されていません')
        return
        }
        else if (e.status == 401){
          alert ('You are not authorized to do this...')
          this._router.navigate(['/home'])
        return
        }
        else if (!e.body){
        alert('データーが見つかれません')
        return
      }
        else if (e.body.length == 1){
          this._router.navigate(['/admin/rirekisho/details/'+e.body[0].shainId])
        } else {
        this._resumeService.sendSearchResults(e.body)
        this._router.navigate(['/admin/rirekisho/results'])
        }
      }, err => console.log(err))
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


