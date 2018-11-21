import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { EmployeeMasterService } from './employee-master.service';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Data } from 'src/app/interfaces/data';
import { Employee } from 'src/app/interfaces/employee';

@Component({
  selector: 'app-employee-master',
  templateUrl: './employee-master.component.html',
  styleUrls: ['./employee-master.component.css']
})
export class EmployeeMasterComponent implements OnInit {

  employeeForm: FormGroup

  
  sex: string[] = ['女','男'];
  support: string[] = ['有','無']
  married: string[] = ['有','無']
  bloodType: String[] = ['A+型','B+型','O+型','AB+型','A-型','B-型','O-型','AB-型']
  position: Data[] = []
  warea: Data[] = []
  affiliation: affiliation[] = []
  

  response$: Observable<any>

  success$: boolean

  carModel: string[] = ['自転車','トラック']

  params$: Observable<Data[]>
  affiliations$: Observable<affiliation[]>

  constructor(private fb: FormBuilder,
              private employeeService: EmployeeMasterService) { 
                this.success$ = false
              }

  ngOnInit() {
    this.affiliations$ = this.employeeService.getAffiliations()
    this.initializeForm()    
    this.params$ = this.employeeService.getViewRendering()
    this.params$.forEach((e) => {
      e.filter((tname) => {
        switch (tname.tname) {
            case 'warea':
            this.warea.push(tname)
            break
            case 'position':
            this.position.push(tname)
            break
        }
      })
    })
  }

  ngOnDestroy(){
    this.success$ = false    
  }

  submitForm(){
    let employee: Employee = this.employeeForm.value
    try {
    this.employeeService.insertShainAttempt(employee)
    this.employeeForm.reset()
    this.success$ = true
    this.initializeForm()
    } catch (err) {
      throw err
    }
    }



  initializeForm(){
    this.employeeForm = this.fb.group({
      shainId: [''],
      shainPassword: [''],
      shainName: [''],
      shainRecruit: [''],
      shainKana: [''],
      shainBirthday: [''],
      shainBloodType: [''],
      shainSex: [''],
      position: this.fb.group ({id: []}),
      affiliation: [],
      shainSupport: false,
      shainMarried: false,
      shainHomePhoneNumber: [''],
      shainMobilePhoneNumber: [''],
      shainMail: [''],
      shainMobileMail: [''],
      shainPostalCode: [''],
      shainAddress: [''],
      shainArea: this.fb.group ({id: []}),
      shainJoinedDate: [''],
      shainRetiredDate: [''],
      shainActive: true,
      shainCarModel: [''],
      role: this.fb.group ({roleid: 1}),
      shainNotes: [''],
      shainRegisterDate: [''],
      shainRegisteredBy: [''],
      shainDeletedFlag: false
    })

  }
}

export interface affiliation{
  affiliation_id: number
  active: boolean
  affiliation_desc: string
}
