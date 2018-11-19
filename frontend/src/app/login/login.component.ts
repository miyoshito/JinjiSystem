import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from './login.service';
import { User } from './login.interface';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../guards/auth.service';
import { BroadcastService } from '../broadcast.service';
import { Observable } from 'rxjs';

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

  constructor(private fb: FormBuilder,
              private loginService: LoginService,
              private route: Router,
              private broadcastService: BroadcastService) { }

  ngOnInit() {

    this.loginform = this.fb.group({
      username: [''],
      password: ['']
    })
    
  }

  login(){    
    const form: User = this.loginform.value
    this.loginService.dologin(form).subscribe(val =>{
    if(val.status === 201) {
        console.log(val.headers.get('Authorization'))        
        this.broadcastService.pushAuthentication(true)
        localStorage.setItem('currentUser',val.headers.get('Authorization'))
        /******************* */
        console.log(val.headers)
        localStorage.setItem('name', val.headers.get('Displayname'))
        /******************* */
        this.route.navigate(['profile']).catch(err => console.log(err))
      } else if (val.status === 401) {
        console.log('401 - unauthorized')
        this.httpStatus = val.status
        alert('Username or Password incorrect, please try again.')
      }
    }, err => {
      console.log(this.httpStatus)
      console.log(err)
      alert('Username or Password incorrect, please try again.')
    })
    
  }


}
