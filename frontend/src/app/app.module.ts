import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { MomentModule, DifferencePipe } from 'ngx-moment';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatFormFieldModule, MatInputModule, MatRadioModule, MatTableModule, MatCheckboxModule, MatToolbarModule, MatSidenavModule, MatButtonModule, MatIconModule, MatMenuModule, MatListModule, MatExpansionModule, MatDatepickerModule, MatNativeDateModule,
  MatPaginatorModule, MatDialogModule
} from '@angular/material'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginService } from './login/login.service';
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './guards/auth-guard.service';
import { JwtModule } from '@auth0/angular-jwt';
import { LoginGuardService } from './guards/login-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { AdminComponent } from './admin/admin.component';
import { EmployeeMasterComponent } from './admin/employee-master/employee-master.component';
import { ResumeComponent } from './resume/resume.component';
import { ResumeSearchComponent } from './resume/resume-search/resume-search.component';
import { ResumeListComponent } from './resume/resume-list/resume-list.component';
import { ResumeDetailsComponent } from './resume/resume-details/resume-details.component';
import { CurriculumComponent } from './curriculum/curriculum.component';
import { CurriculumSearchComponent } from './curriculum/curriculum-search/curriculum-search.component';
import { CurriculumListComponent } from './curriculum/curriculum-list/curriculum-list.component';
import { CurriculumDetailsComponent } from './curriculum/curriculum-details/curriculum-details.component';
import { CurriculumInsertComponent } from './curriculum/curriculum-insert/curriculum-insert.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { TokenInterceptorService } from './guards/token-interceptor.service';
import { EmployeeListComponent } from './admin/employee-list/employee-list.component';
import { ResumeAddComponent } from './resume/resume-add/resume-add.component';
import { FileUploadModule } from 'ng2-file-upload';
import { SkillMapComponent } from './skill-map/skill-map.component';
import { SystemSettingsComponent } from './admin/system-settings/system-settings.component';
import { SkillMapSearchComponent } from './admin/skill-map-search/skill-map-search.component';
import { ValidatorsComponent } from './validators/validators.component';
import { StudycourseComponent } from './studycourse/studycourse.component';
import { QualificationsComponent } from './qualifications/qualifications.component';
import { AdminHeaderComponent } from './header/admin-header/admin-header.component';
import { UserHeaderComponent } from './header/user-header/user-header.component';
import { ManagerHeaderComponent } from './header/manager-header/manager-header.component';
import { BooleanPipe } from './boolean-pipe';
import { CustomDialogComponent } from './custom-dialog/custom-dialog.component';
import { ResumeSearchResultsComponent } from './resume/resume-search-results/resume-search-results.component';
import { LoadingDialogComponent } from './loading-dialog/loading-dialog.component';
import { EmployeeSearchComponent } from './admin/employee-search/employee-search.component';
import { EmployeeDetailsComponent } from './admin/employee-details/employee-details.component';
import { MonthPickerComponent } from './utils/month-picker/month-picker.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker'
import { defineLocale } from 'ngx-bootstrap/chronos';
import { jaLocale } from 'ngx-bootstrap/locale';

export function tokenGetter() {
  return localStorage.getItem('currentUser');
}
defineLocale('ja',jaLocale)

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ProfileComponent,
    AdminComponent,
    EmployeeMasterComponent,
    ResumeComponent,
    ResumeSearchComponent,
    ResumeListComponent,
    ResumeDetailsComponent,
    CurriculumComponent,
    CurriculumSearchComponent,
    CurriculumListComponent,
    CurriculumDetailsComponent,
    CurriculumInsertComponent,
    NotFoundComponent,
    EmployeeListComponent,
    ResumeAddComponent,
    SkillMapComponent,
    SystemSettingsComponent,
    SkillMapSearchComponent,
    ValidatorsComponent,
    StudycourseComponent,
    QualificationsComponent,
    AdminHeaderComponent,
    UserHeaderComponent,
    ManagerHeaderComponent,
    BooleanPipe,
    CustomDialogComponent,
    ResumeSearchResultsComponent,
    LoadingDialogComponent,
    EmployeeSearchComponent,
    EmployeeDetailsComponent,
    MonthPickerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatTableModule,
    MatCheckboxModule,
    MatToolbarModule,
    MatSidenavModule,
    MatExpansionModule,
    MatListModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: ['localhost:8080/api/auth']
      }
    }),
    MatRadioModule,
    NgSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatPaginatorModule,
    FileUploadModule,
    MatDialogModule,
    MomentModule,
    BsDatepickerModule.forRoot()
  ],
  exports: [
    [BooleanPipe]
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    LoginService,
    AuthGuardService,
    LoginGuardService,
    TokenInterceptorService,
    DifferencePipe
  ],
  bootstrap: [AppComponent],
  entryComponents: [CustomDialogComponent]
})
export class AppModule { }
