import { Component, OnInit, Input, Output, EventEmitter, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-custom-dialog',
  templateUrl: './custom-dialog.component.html',
  styleUrls: ['./custom-dialog.component.css']
})
export class CustomDialogComponent implements OnInit {

  constructor(private _dialog: MatDialog,
              public dialogRef: MatDialogRef<CustomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  @Input('title')
  title$ = new Observable<String>()

  @Input('message')
  message$ = new Observable<String>()

  @Output('authorization')
  authorization = new EventEmitter<boolean>()
  

  ngOnInit() {

  }

  loadDialog(){
  }

  onNoClick(){
    this.dialogRef.close()
  }
  authorized(){
    this.dialogRef.close(true)
  }




}
