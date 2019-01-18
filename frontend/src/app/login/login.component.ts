import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { User } from 'src/app/interfaces/login.interface';
import { map, takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/guards/auth.service';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { Observable, Subscription, Subject } from 'rxjs';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginform: FormGroup
  submitted: boolean
  httpStatus = null
  loggedUsername: Observable<String>
  isLoggedIn: boolean = false;

  sub: Subscription
  isAlive$: Subject<boolean> = new Subject<boolean>()


  authFailed$: boolean
  redirecting$: boolean

  constructor(private fb: FormBuilder,
              private loginService: LoginService,
              private route: Router,
              private broadcastService: BroadcastService,
              private profileService: ProfileService)
              {
                this.authFailed$ = false;
                this.redirecting$ = false;
              }

  ngOnInit() {

    this.loginform = this.fb.group({
      username: [''],
      password: ['']
    })
    
  }

  login(){    
    if(localStorage.getItem('currentUser') != null){localStorage.removeItem('currentUser')}
    let form: User = new User('','')
    form.username =  this.loginform.get("username").value
    form.password = this.loginform.get("password").value
    this.loginService.doLogin(form)    
    .pipe(
      takeUntil(this.isAlive$),
      map(res => {
        localStorage.setItem('currentUser', res.headers.get('Authorization'))        
        this.profileService.getLoggedInUserData()
        this.authFailed$ = false;
        this.redirecting$ = true;         
        this.route.navigate(['home'])
      })).subscribe(e =>{},err =>{
        this.authFailed$ = true
      })
  }

  ngOnDestroy(){
    this.isAlive$.next()
  }
}
