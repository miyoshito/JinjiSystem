import { Component, OnInit } from '@angular/core';
import { BroadcastService } from '../broadcast.service';
import { AuthService } from '../guards/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
  }

}
