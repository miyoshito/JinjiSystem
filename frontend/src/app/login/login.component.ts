import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from './login.service';
import { User } from './login.interface';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../guards/auth.service';
import { BroadcastService } from '../broadcast.service';
import { Observable, Subscription } from 'rxjs';
import { ProfileService } from '../profile/profile.service';

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
    const form: User = this.loginform.value
    this.sub = this.loginService.doLogin(form)    
    .subscribe(res => {
        localStorage.setItem('currentUser', res.headers.get('Authorization'))
        this.authFailed$ = false;
        this.redirecting$ = true;
        this.profileService.cacheUser(); // -> isso aqui ja me traz as infos da pessoa logada pra buildar o perfil (tenho a auth_role)
        setTimeout(() =>{
          this.broadcastService.pushAuthentication(true);
          this.route.navigate(['profile'])
        },3000)
    },
    err => {
      console.log(err)
      this.authFailed$ = true
    })
  }

  ngOnDestroy(){
    this.sub.unsubscribe()
  }
}
