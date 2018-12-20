import { Component, OnInit } from '@angular/core';
import { Employee } from 'src/app/interfaces/employee';
import { Observable } from 'rxjs';
import { ProfileService } from 'src/app/profile/profile.service';
import { map } from 'rxjs/operators';
import { CurriculumService } from 'src/app/curriculum/curriculum.service';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';


@Component({
  selector: 'app-skill-map-details',
  templateUrl: './skill-map-details.component.html',
  styleUrls: ['./skill-map-details.component.css']
})
export class SkillMapDetailsComponent implements OnInit {

  constructor(private _profileService: ProfileService,
              private _cuurriculumService: CurriculumService,
              private _employeeMasterService: EmployeeMasterService) { }

  allUsers: Observable<Employee[]>

  selectedUsers$: Observable<Employee[]>
  displayedColumns: Array<string> = ['DBMS','OS']
  columnCount$: Observable<any[]>
  zzz: string[]

  displayArray: SkillMap[] = []
  display: SkillMap
  holder: experience

  ngOnInit() {
    this.columnCount$ = this._cuurriculumService.getPropertiesList()
    this.selectedUsers$ = this._employeeMasterService.searchResults$
    this._employeeMasterService.getAllShains()

    this.buildMap()
  }

  buildMap(){
    //criar uma lista pra armazenar os dados que vao ser exibidos
    this.selectedUsers$.pipe(
      map(user => { //passando por todos os users da lista
        user.forEach((e,i) => { //pra cada employee que eu tiver na lista, fazer um loop em todos os CVS que ele tem.
          e.curriculum.forEach(cv =>{ //verificar se o cv possui langs
            if(cv.langData){
              cv.langData.forEach(lang => {
                this.displayArray[i].langs
              })
            }
          })

        })
        
      })
    ).subscribe()

  }


   

}

export interface SkillMap{
  nome: string
  affiliation: string
  langs: experience[]
}
export class SkillMap{
  constructor(
    nome: string,
    affiliation: string,
    langs: experience[]
  ){
    this.nome = nome
    this.affiliation = affiliation
    this.langs = langs
  }
}

export interface experience{
  desc: string
  expTime: number
}
