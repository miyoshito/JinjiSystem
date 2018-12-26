import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { LoginComponent } from 'src/app/login/login.component'
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './guards/auth-guard.service';
import { LoginGuardService } from './guards/login-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { EmployeeMasterComponent } from './admin/employee-master/employee-master.component';
import { ResumeDetailsComponent } from './resume/resume-details/resume-details.component';
import { ResumeListComponent } from './resume/resume-list/resume-list.component';
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
import { QualificationsComponent } from './qualifications/qualifications.component';
import { StudycourseComponent } from './studycourse/studycourse.component';
import { RoleGuardService } from './guards/role-guard.service';
import { ResumeSearchResultsComponent } from './resume/resume-search-results/resume-search-results.component';
import { EmployeeSearchComponent } from './admin/employee-search/employee-search.component';
import { SkillMapDetailsComponent } from './skill-map/skill-map-details/skill-map-details.component';
import { StudyCourseSearchComponent } from './studycourse/study-course-search/study-course-search.component';
import { StudyCourseEditComponent } from './studycourse/study-course-edit/study-course-edit.component';
import { StudyCourseResultsComponent } from './studycourse/study-course-results/study-course-results.component';

const routes: Routes = [
  // Login routes
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: '404', component: NotFoundComponent},
  {path: 'home', component: HomeComponent},
  
  {path: 'login', component: LoginComponent, canActivate:[LoginGuardService]},
  // User parent routes

  //URL de direcionamento padrao de todos os usuarios
  {path: 'profile', children:[
    {path: '', component: EmployeeMasterComponent},
    {path: 'shokumurirekisho', component: CurriculumDetailsComponent},
    {path: 'shokumurirekisho/add', component: CurriculumInsertComponent},
    {path: 'shokumurirekisho/edit/:shid', component: CurriculumInsertComponent},
    {path: 'skillmap', component: SkillMapDetailsComponent},
    {path: 'qualifications', component: QualificationsComponent},
    {path: 'studycourses', component: StudycourseComponent},
  ],canActivate:[AuthGuardService]},

  //URL de direcionamento pra quem tem permissoes de administrador...
  {path: 'admin',
    children:[
      {path: 'employee-master', component: EmployeeMasterComponent},
      {path: 'employee-list', component: EmployeeListComponent},
      {path: 'employee-search', component: EmployeeSearchComponent},
      {path: 'skillmap', component: SkillMapDetailsComponent},
      {path: 'systemsettings', component: SystemSettingsComponent}, //temp
      //resumes...
      {path: 'rirekisho', children:[
          {path: 'add', component: ResumeAddComponent},
          {path: 'edit/:id', component: ResumeAddComponent},
          {path: 'search', component: ResumeSearchComponent},
          {path: 'results', component: ResumeSearchResultsComponent},
          {path: 'details/:id', component: ResumeDetailsComponent}
      ]},
      {path: 'shokumurirekisho', children:[
          {path: 'edit/:uid/:shid', component: CurriculumInsertComponent},
          {path: 'details/:id', component: CurriculumDetailsComponent},
          {path: 'add', component: CurriculumInsertComponent},
          {path: 'search', component: CurriculumSearchComponent},
          {path: 'list', component: CurriculumListComponent}
      ]},
      {path: 'profile/:id', children:[
        {path: '', component: ProfileComponent},
        {path: 'edit', component: EmployeeMasterComponent},
        {path: 'resume', component: ResumeListComponent},
        {path: 'resume/add', component: ResumeAddComponent},
        {path: 'resume/edit', component: ResumeAddComponent},
        {path: 'curriculum/add', component: CurriculumInsertComponent},
        {path: 'curriculum/edit', component: CurriculumInsertComponent}
      ]},
      {path: 'studycourse', children: [
        {path: 'list', component: StudyCourseResultsComponent},
        {path: 'search', component: StudyCourseSearchComponent},
        {path: ':sid/:scid/edit', component: StudyCourseEditComponent}
      ]}
  ],canActivate:[RoleGuardService]},
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
