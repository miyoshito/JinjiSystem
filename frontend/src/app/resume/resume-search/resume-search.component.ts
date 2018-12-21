import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SearchForm } from '../resume-details-interface';
import { ResumeService } from '../resume.service';
import { Router } from '@angular/router';
import { BroadcastService } from 'src/app/broadcast.service';
import { Subject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { ProfileService } from 'src/app/profile/profile.service';
import { LoginService } from 'src/app/login/login.service';
import { atLeastOne } from 'src/app/validators/atleastOne';

@Component({
  selector: 'app-resume-search',
  templateUrl: './resume-search.component.html',
  styleUrls: ['./resume-search.component.css']
})
export class ResumeSearchComponent implements OnInit {

  searchForm: FormGroup

  active$: Subject<boolean>

  searchParam: SearchForm

  age = new Array

  constructor(private _fb: FormBuilder,
              private _resumeService: ResumeService,
              private _profileService: ProfileService,
              private _router: Router,
              private _loginService: LoginService) { }

  ngOnInit() {
    this.initializeForm()
    for (let i = 18; i <= 90; i++){
      this.age.push(i)
    }
  }

  
  searchAttempt(){
    if (!this.searchForm.valid)
    {
      alert ('atleast 1 field needs to be filled')
      return
    }    
    this._resumeService.searchResumeAttempt(this.searchParam)
    .pipe(
      map(res => {
      if (!res.body.length) {
        alert('データーが見つかりません')
      } else if (res.body.length > 1) {
      this._resumeService.sendSearchResults(res.body)
      this._router.navigate(['/admin/rirekisho/results'])
      } else {
      this._router.navigate(['/admin/rirekisho/details/'+res.body[0].shainId])
      }
    })).subscribe(
    res => {},
    err => {
      console.log(err)
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
      bunri: [''],
      career: [''],
      qualification: [''],
    }, {validator: atLeastOne(Validators.required)})
  }


}


