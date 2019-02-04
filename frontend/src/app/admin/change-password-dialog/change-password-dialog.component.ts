import { Component, OnInit, Input, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeMasterService } from 'src/app/services/employee-master.service';

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.css']
})
export class ChangePasswordDialogComponent implements OnInit {

  changepassform: FormGroup

  empid: string

  constructor(
    private _dialog: MatDialog,
    public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _fb: FormBuilder,
    private _employeeMaster: EmployeeMasterService
    ) { }

  @Input('title')
  title$ = new Observable<String>()

  @Input('message')
  message$ = new Observable<String>()

  @Input('anything') e = new Observable<String>()
  

  ngOnInit() {
  this.buildForm()
  }

  buildForm(){
    this.changepassform = this._fb.group({      
      oldpassword: ['', Validators.required],
      newpassword: ['', Validators.required],
      newpassword2: ['', Validators.required],
    })
  }

  cancel(){
    this.dialogRef.close()
  }

  changePasswordRequest(id: string){
    if (!this.changepassform.valid){
      return
    }
    else if(this.changepassform.get('newpassword').value != this.changepassform.get('newpassword2').value){
      alert('新しいパスワード doesnt match')
      return
    } else {
      this._employeeMaster.changePassword(id,this.changepassform.get('oldpassword').value, this.changepassform.get('newpassword').value)
      .subscribe(res => {
        if (res.status == 200){
          alert('Password Changed')
          this.dialogRef.close()
        }
      }, err => {
        alert('Old password incorrect, try again...')
      })
    }
    
  }

}
