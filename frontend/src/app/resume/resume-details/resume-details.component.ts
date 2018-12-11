import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/interfaces/employee';
import { ProfileService } from 'src/app/profile/profile.service';

@Component({
  selector: 'app-resume-details',
  templateUrl: './resume-details.component.html',
  styleUrls: ['./resume-details.component.css']
})
export class ResumeDetailsComponent implements OnInit {

  constructor(private _router: Router,
              private _profileService: ProfileService,
              private _route: ActivatedRoute) { }

  userSelected$: Observable<Employee>

  ngOnInit() {
    this.userSelected$ = this._profileService.getUserProfile(this._route.snapshot.paramMap.get('id'))
  }

  editScreen(id: number){
    this._router.navigate(['/admin/rirekisho/edit/'+id])
  }

}
