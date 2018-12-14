import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CurriculumService } from '../curriculum.service';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { Employee } from 'src/app/interfaces/employee';
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
              private _fb: FormBuilder,
              private localeService: BsLocaleService) {
                localeService.use('ja')
   }

  bsValue: Date = new Date(2017, 7);
  endValue: Date = new Date(2018,10)
  minMode: BsDatepickerViewMode = 'month';
  bsConfig: Partial<BsDatepickerConfig>;

  cvForm: FormGroup
  indForm: FormGroup
  data$: Observable<any[]>
  loggedUser$: Observable<Employee>

  typeSelected$: Subject<boolean> = new Subject<boolean>()
  type: number

  industryDropdown$: Observable<any[]>

  ngOnInit() {
    this.loggedUser$ = this._profileService.cachedUser$
    this.data$ = this.cvService.getPropertiesList()
    this.industryDropdown$ = this.cvService.getBusinessLogic()
    this.generateForm()
    this.industryForm()  
    
    this.bsConfig = Object.assign(
      {minMode : this.minMode},
      {containerClass: "theme-red"},
      {dateInputFormat : 'YYYY/MMMM'});   
  }

  ngOnDestroy(): void {
  }

  industryForm(): FormGroup{    
    return this._fb.group({
      industryid: [],
      classid: [],
    })
  }
  changeChilds(id: number){
    this.typeSelected$.next(true);
    this.type = id - 1
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

