import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { LoginComponent } from 'src/app/login/login.component'
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './guards/auth-guard.service';
import { LoginGuardService } from './guards/login-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { AdminComponent } from './admin/admin.component';
import { EmployeeMasterComponent } from './admin/employee-master/employee-master.component';
import { ResumeComponent } from './resume/resume.component';
import { ResumeDetailsComponent } from './resume/resume-details/resume-details.component';
import { ResumeListComponent } from './resume/resume-list/resume-list.component';
import { CurriculumComponent } from './curriculum/curriculum.component';
import { CurriculumSearchComponent } from './curriculum/curriculum-search/curriculum-search.component';
import { CurriculumListComponent } from './curriculum/curriculum-list/curriculum-list.component';
import { CurriculumDetailsComponent } from './curriculum/curriculum-details/curriculum-details.component';
import { ResumeSearchComponent } from './resume/resume-search/resume-search.component';
import { CurriculumInsertComponent } from './curriculum/curriculum-insert/curriculum-insert.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { EmployeeListComponent } from './admin/employee-list/employee-list.component';

const routes: Routes = [
  // Login routes
  {path: '', redirectTo: 'login',pathMatch: 'full'},
  {path: '404', component: NotFoundComponent},
  
  {path: 'login', component: LoginComponent, canActivate:[LoginGuardService]},
  // User parent routes

  //URL de direcionamento padrao de todos os usuarios
  {path: 'profile', children:[
    {path: '', component: ProfileComponent},
    {path: 'edit', component: NotFoundComponent}, // -> Edit em dados pessoais (telefone, e-mail e blablabla)
    {path: 'resume/add', component: ResumeSearchComponent},
    {path: 'resume/{id}/edit', component: ResumeSearchComponent},
    {path: 'curriculum/add', component: CurriculumInsertComponent},
    {path: 'curriculum/{id}/edit', component: ResumeSearchComponent},
  ]},

  //URL de direcionamento pra quem tem permissoes de administrador...
  {path: 'admin',
    children:[
      {path: 'employee-master', component: EmployeeMasterComponent},
      {path: 'employee-list', component: EmployeeListComponent},
      {path: 'systemsettings', component: AdminComponent}, //temp
        {path: 'resume', children:[
          {path: 'search', component: ResumeSearchComponent},
          {path: 'list', component: ResumeListComponent},
          {path: '{shainbangou}/{id}/details', component: ResumeDetailsComponent}
        ]},
      {path: 'curriculum', children:[
          {path: 'add', component: CurriculumInsertComponent},
          {path: 'search', component: CurriculumSearchComponent},
          {path: 'list', component: CurriculumListComponent},
          {path: '{shainbangou}/{id}/details', component: CurriculumDetailsComponent},
      ]}
  ]},
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
