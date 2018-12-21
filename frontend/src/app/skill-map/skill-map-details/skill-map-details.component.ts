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
  mapping$: Observable<any[]>
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
        map((d, i) =>{
            this.displayMap.push(new SkillMapData(
              d[i].id,
              d[i].name,
              d[i].name,
              d[i].affiliation,
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience),
              new SkillMapParams[0](d[i].params.lang.description, d[i].params.lang.experience))
              )
          })
      ).subscribe()

      console.log(this.displayMap)
    }
  }

  buildMap(){
    let t: SkillMapData
  }


   

}


