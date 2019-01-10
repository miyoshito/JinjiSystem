import { Component, OnInit } from '@angular/core';
import { BroadcastService } from 'src/app/services/broadcast.service';
import { AuthService } from 'src/app/services/guards/auth.service';

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
