import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders, HttpResponse, HttpEventType }   from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(public auth: AuthService) { }

  //httpHeaders: HttpHeaders = new HttpHeaders().append("Authorization", localStorage.getItem('currentUser'))


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {   
    let request
    if (localStorage.getItem('currentUser') != null) {
    request = req.clone({
      setHeaders: {
        'Authorization': localStorage.getItem('currentUser')
      }        
    })
    }
    else request = req.clone({})
    console.log(request)
    return next.handle(request)
  }
  }
