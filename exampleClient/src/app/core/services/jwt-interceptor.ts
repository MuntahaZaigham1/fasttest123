import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available


	request = request.clone({ headers: request.headers.set('Accept', 'application/json')});
	
	if (request.headers.get('content-type') == "multipart/form-data") {
      request = request.clone({ headers: request.headers.delete('content-type','multipart/form-data') });
    } else {
      request = request.clone({ headers: request.headers.set('content-type', 'application/json')});
    }
    

    if(request.url.includes(environment.apiUrl)){
      request = request.clone({
        withCredentials: true,
      })
    }

    return next.handle(request);
  }
}