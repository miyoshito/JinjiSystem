import { Component, OnInit } from '@angular/core';
import { Observable, from, forkJoin, Subscription } from 'rxjs';
import { CurriculumService } from '../curriculum.service';
import { Data } from 'src/app/interfaces/data';
import {NgSelectModule, NgOption} from '@ng-select/ng-select';
import { map, switchMap, merge, mergeMap, flatMap, filter, tap } from 'rxjs/operators';
import { Employee } from 'src/app/interfaces/employee';
import { BroadcastService } from 'src/app/broadcast.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-curriculum-insert',
  templateUrl: './curriculum-insert.component.html',
  styleUrls: ['./curriculum-insert.component.css']
})
export class CurriculumInsertComponent implements OnInit {

  constructor(private cvService: CurriculumService,
              private _profileService: ProfileService,
              private _fb: FormBuilder) {
   }

  cvForm: FormGroup
  indForm: FormGroup
  data$: Observable<any[]>
  loggedUser$: Observable<Employee>

  ngOnInit() {
    this.loggedUser$ = this._profileService.cachedUser$
    this.data$ = this.cvService.getPropertiesList()
    this.generateForm()
    this.industryForm()

    this.cvService.makeBusinessStructure()
  }

  ngOnDestroy(): void {
  }

  industryForm(): FormGroup{    
    return this._fb.group({
      industryid: [],
      classid: [],
    })
  }

  generateForm(){
    this.cvForm = this._fb.group({
      startperiod: [],
      endperiod: [],
      targetbusiness: [''],
      customer: [''],
      industry: [''],
      makerData:    { id: [] },
      osData:       { id: [] },
      dbmsData:     { id: [] },
      responseData: { id: [] },
      langData:     { id: [] },
      toolsData:    { id: [] },
    })

  }

}
