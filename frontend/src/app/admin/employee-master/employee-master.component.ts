import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { EmployeeMasterService } from './employee-master.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
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
  affiliation: Data[] = []

  response$: Observable<any>

  carModel: string[] = ['自転車','トラック']

  params$: Observable<Data[]>

  constructor(private fb: FormBuilder,
              private employeeService: EmployeeMasterService) { }

  ngOnInit() {
    this.initializeForm()
    this.params$ = this.employeeService.emParam()
    this.params$.forEach((e) => {
      e.filter((tname) => {
        switch (tname.tname) {
            case 'warea':
            this.warea.push(tname)
            break
            case 'position':
            this.position.push(tname)
            break
            case 'affiliation':
            this.affiliation.push(tname)
            break
        }
      })
    })

    console.log(this.affiliation)
  }

  submitForm(){
    let employee: Employee = this.employeeForm.value
    try {
    this.employeeService.insertShainAttempt(employee)
    this.employeeForm.reset()
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
      affiliation: this.fb.group ({id: []}),
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
