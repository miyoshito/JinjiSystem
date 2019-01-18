import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { LoginComponent } from 'src/app/login/login.component'
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from 'src/app/services/guards/auth-guard.service';
import { LoginGuardService } from 'src/app/services/guards/login-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { EmployeeMasterComponent } from './admin/employee-master/employee-master.component';
import { ResumeDetailsComponent } from './resume/resume-details/resume-details.component';
import { CurriculumSearchComponent } from './curriculum/curriculum-search/curriculum-search.component';
import { CurriculumListComponent } from './curriculum/curriculum-list/curriculum-list.component';
import { CurriculumDetailsComponent } from './curriculum/curriculum-details/curriculum-details.component';
import { ResumeSearchComponent } from './resume/resume-search/resume-search.component';
import { CurriculumInsertComponent } from './curriculum/curriculum-insert/curriculum-insert.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { EmployeeListComponent } from './admin/employee-list/employee-list.component';
import { ResumeAddComponent } from './resume/resume-add/resume-add.component';
import { SkillMapComponent } from './skill-map/skill-map.component';
import { SystemSettingsComponent } from './admin/system-settings/system-settings.component';
import { RoleGuardService } from 'src/app/services/guards/role-guard.service';
import { ResumeSearchResultsComponent } from './resume/resume-search-results/resume-search-results.component';
import { EmployeeSearchComponent } from './admin/employee-search/employee-search.component';
import { SkillMapDetailsComponent } from './skill-map/skill-map-details/skill-map-details.component';
import { StudyCourseSearchComponent } from './studycourse/study-course-search/study-course-search.component';
import { StudyCourseEditComponent } from './studycourse/study-course-edit/study-course-edit.component';
import { StudyCourseResultsComponent } from './studycourse/study-course-results/study-course-results.component';
import { StudyCourseDetailsComponent } from './studycourse/study-course-details/study-course-details.component';
import { SoumuGuardService } from './services/guards/soumu-guard.service';
import { AdminGuardService } from './services/guards/admin-guard.service';
import { QualificationsSearchComponent } from './qualifications/qualifications-search/qualifications-search.component';

const routes: Routes = [
  // Login routes
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: '404', component: NotFoundComponent},
  {path: 'home', component: HomeComponent},  
  {path: 'login', component: LoginComponent, canActivate:[LoginGuardService]},
  
  // User parent routes

  {path: 'public', children:[
    {path: 'skillmap', component: SkillMapDetailsComponent},
    {path: 'profile', component: EmployeeMasterComponent},
    {path: 'qualifications', children:[
      {path: 'search', component: QualificationsSearchComponent}
    ]},
    {path: 'shokumurirekisho', children:[
      {path: '', component: CurriculumDetailsComponent},
      {path: 'add', component: CurriculumInsertComponent},
      {path: 'search', component: CurriculumSearchComponent, canActivate: [RoleGuardService]},
      {path: 'list', component: CurriculumListComponent, canActivate: [RoleGuardService]},
      {path: ':uid/add', component: CurriculumInsertComponent, canActivate: [RoleGuardService]},
      {path: 'details/:id', component: CurriculumDetailsComponent, canActivate: [RoleGuardService]},
      {path: 'edit/:uid/:shid', component: CurriculumInsertComponent}
    ]},

    {path: 'studycourse', children:[
      {path: '', component: StudyCourseDetailsComponent},
      {path: 'add', component: StudyCourseEditComponent},
      {path: ':scid/edit', component: StudyCourseEditComponent},
      {path: 'search', component: StudyCourseSearchComponent, canActivate: [RoleGuardService]},          
      {path: 'details/:uid', component: StudyCourseDetailsComponent, canActivate: [RoleGuardService]},
      {path: 'list', component: StudyCourseResultsComponent, canActivate: [RoleGuardService]},
      {path: ':uid/:scid/edit', component: StudyCourseEditComponent, canActivate: [RoleGuardService]},
      {path: ':uid/add', component: StudyCourseEditComponent, canActivate: [RoleGuardService]}
    ]},
  ]},

  {path: 'soumu', children:[
    {path: 'rirekisho', children:[
      {path: 'add', component: ResumeAddComponent},
      {path: 'edit/:id', component: ResumeAddComponent},
      {path: 'search', component: ResumeSearchComponent},
      {path: 'results', component: ResumeSearchResultsComponent},
      {path: 'details/:id', component: ResumeDetailsComponent}
    ]}
  ], canActivate: [SoumuGuardService]},

  //URL de direcionamento pra quem tem permissoes de administrador...
  {path: 'admin', children:[
      {path: 'employee-master', component: EmployeeMasterComponent},
      {path: 'employee-list', component: EmployeeListComponent},
      {path: 'employee-search', component: EmployeeSearchComponent},
      {path: 'systemsettings', component: SystemSettingsComponent},
      {path: 'profile/:id/edit', component: EmployeeMasterComponent},
      {path: 'systemsettings', component: SystemSettingsComponent }
  ], canActivate: [AdminGuardService]},
  //mapeando o direcionamento pra paginas inexistentes...
  {path: '**', redirectTo: '404' }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
