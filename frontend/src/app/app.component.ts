import { Component, OnInit } from '@angular/core';
import { AuthService } from './guards/auth.service';
import { RoleGuardService } from './guards/role-guard.service';
import { BroadcastService } from './broadcast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor(private auth: AuthService){}

  ngOnInit(): void {
    let header = this.auth.isAuthenticated();
  }
}
