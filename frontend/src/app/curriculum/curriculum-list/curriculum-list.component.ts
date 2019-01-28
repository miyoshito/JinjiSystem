import { Component, OnInit } from '@angular/core';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { Observable } from 'rxjs';
import { Employee, Curriculum } from 'src/app/interfaces/employee';
import { Router } from '@angular/router';

@Component({
  selector: 'app-curriculum-list',
  templateUrl: './curriculum-list.component.html',
  styleUrls: ['./curriculum-list.component.css']
})
export class CurriculumListComponent implements OnInit {

  constructor(private _curriculumService: CurriculumService,
              private _router: Router) { }

  selectedUsers$: Observable<Employee[]>

  experienceTimeDisplay = new Array

  p: number = 1

  ngOnInit() {
    this.selectedUsers$ = this._curriculumService.selectedUser$
  }

  showRirekisho(id: string){
    this._router.navigate(['/public/shokumurirekisho/details/'+id])
  }


  sumOf(cv?: Curriculum[]): number{
    let total: number = 0
    for(let c of cv){
      if (!c.deleted){
      total += c.experienceTime
      }
    }
    return total
  }

  getOsandDb(cv?: Curriculum[]) {
    let data: String[] = []
    
    for(let c of cv){
      if (!c.deleted){
      c.osData.forEach(o =>{data.push(' '+o.desc)})
      c.dbmsData.forEach(d =>{data.push(' '+d.desc)})
      }
    }
    let correctedData = Array.from(new Set(data))
    return correctedData
  }

  getToolsandLang(cv?: Curriculum[]) {
    let data: String[] = []
    for(let c of cv){
      if (!c.deleted){
      c.makerData.forEach(m =>{data.push(' '+m.desc)})
      c.toolsData.forEach(t =>{data.push(' '+t.desc)})
      }
    }
    let correctedData = Array.from(new Set(data))
    return correctedData
  }

}
