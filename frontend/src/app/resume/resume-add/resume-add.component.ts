import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { API_URL } from '../../url-settings'

const url = API_URL

@Component({
  selector: 'app-resume-add',
  templateUrl: './resume-add.component.html',
  styleUrls: ['./resume-add.component.css']
})
export class ResumeAddComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  public uploader:FileUploader = new FileUploader({url: url});
}
