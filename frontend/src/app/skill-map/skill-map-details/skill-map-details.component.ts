import { Component, OnInit } from '@angular/core';
import { Employee } from 'src/app/interfaces/employee';
import { Observable, Subject } from 'rxjs';
import { ProfileService } from 'src/app/profile/profile.service';
import { map, takeUntil } from 'rxjs/operators';
import { CurriculumService } from 'src/app/curriculum/curriculum.service';
import { EmployeeMasterService } from 'src/app/admin/employee-master/employee-master.service';
import { SkillMapData, SkillMapParams } from 'src/app/interfaces/skillmap';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/guards/auth.service';
import { t } from '@angular/core/src/render3';


@Component({
  selector: 'app-skill-map-details',
  templateUrl: './skill-map-details.component.html',
  styleUrls: ['./skill-map-details.component.css']
})
export class SkillMapDetailsComponent implements OnInit {

  constructor(private _profileService: ProfileService,
              private _curriculumService: CurriculumService,
              private _router: Router,
              private _authService: AuthService) { }

  columnCount$: Observable<any[]>
  mapping$: Observable<SkillMapData[]>
  myself: string

  displayMap: SkillMapData[] = []

  unsub$: Subject<boolean> = new Subject<boolean>();
  

  ngOnInit() {

    

    this.columnCount$ = this._curriculumService.getPropertiesList()
    this.mapping$ = this._curriculumService.SkillMapSearchResults$
    if (this._router.url.startsWith('/profile')){
      this._profileService.cachedUser$.pipe(
        takeUntil(this.unsub$),
        map(e => this._curriculumService.getSkillMaps(e.shainId))
      ).subscribe()
      //remontando o mapa LOL
      this.mapping$.pipe(
        map((sm, index) =>{
          sm[index].params.forEach(param =>{
          })
            //document.getElementById(la.description+index).setAttribute('value',la.experience.toString())
        })               
      ).subscribe()
    }
  }

  buildMap(){
    let t: SkillMapData
  }


   

}
export interface ggg{
  description: string
  experience: number
}

