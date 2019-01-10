import { Component, OnInit, AfterViewInit, AfterViewChecked, ElementRef, Renderer2, ViewChildren, ViewContainerRef, QueryList } from '@angular/core';
import { Employee } from 'src/app/interfaces/employee';
import { Observable, Subject } from 'rxjs';
import { ProfileService } from 'src/app/services/profile.service';
import { map, takeUntil } from 'rxjs/operators';
import { CurriculumService } from 'src/app/services/curriculum.service';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';
import { SkillMapData, SkillMapParams } from 'src/app/interfaces/skillmap';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/guards/auth.service';
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
              private _authService: AuthService,
              private _renderer: Renderer2,
              private _elemRef: ElementRef) { }

  columnCount$: Observable<any[]>
  mapping$: Observable<SkillMapData[]>
  myself: string

  displayMap: SkillMapData[] = []


  columnsToDisplay: string[]

  unsub$: Subject<boolean> = new Subject<boolean>();

  @ViewChildren('lang', {read: ElementRef})
  langs: QueryList<ElementRef>
  

  ngOnInit() {
    /*this.columnCount$ = this._curriculumService.getPropertiesList()
    this.mapping$ = this._curriculumService.SkillMapSearchResults$
    this.gambiarradoida()
    if (this._router.url.startsWith('/profile')){
      this._profileService.cachedUser$.pipe(
        takeUntil(this.unsub$),
        map(e => this._curriculumService.getSkillMaps(e.id))
      ).subscribe()
    }*/
  }

  ngAfterViewInit(){
   this.buildEmployeeLine()
  }

  gambiarradoida() {
    let uwu:Array<number>[] = []
    //no caso de todas as infos
    this.columnCount$.pipe(
      map(cc => {
        
      })
    ).subscribe()

    this.mapping$.pipe(
      map(usr => {
        usr.forEach((u,i) => {
          console.log(i)
          //for(let la of u.params.LANG){
           // uwu.push(la.experience)
          //}
        })
      })
    ).subscribe()
    console.log(uwu)
  }

  buildEmployeeLine(){
    console.log('.,....')
    this.langs.forEach(e => {
    console.log(e.nativeElement)
    })
  }
    
}
export interface ggg{
  description: string
  experience: number
}

