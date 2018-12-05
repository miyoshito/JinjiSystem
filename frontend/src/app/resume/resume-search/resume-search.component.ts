import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SearchForm } from '../resume-details-interface';
import { ResumeService } from '../resume.service';
import { Router } from '@angular/router';
import { BroadcastService } from 'src/app/broadcast.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ProfileService } from 'src/app/profile/profile.service';

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
              private _router: Router) { }

  ngOnInit() {
    this.initializeForm()
    for (let i = 18; i <= 90; i++){
      this.age.push(i)
    }
  }

  
  searchAttempt(){
    this.searchParam = this.searchForm.value
    console.log(this.searchParam)
    this._resumeService.searchResumeAttempt(this.searchParam)
    .subscribe(res =>{
      console.log(res)
      if (!res.length) {
        alert('nothing found')
      } else if (res.length > 1) {
      this._resumeService.sendSearchResults(res)
      console.log(res)
      this._router.navigate(['/admin/rirekisho/results']) 
      } else {
      this._profileService.getUserProfile(res[0].shainId)
      console.log(res)
      this._router.navigate(['home']) 
      }
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
    })
  }


}


