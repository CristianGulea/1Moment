import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import { BehaviorSubject } from 'rxjs';
import {User} from "./User";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  user: BehaviorSubject<User> = new BehaviorSubject<User>({username: "", password: "", accessToken: "", refreshToken:""});

  constructor(private http: HttpClient, private routes: Router) { }

  public handleLoginByCredentials(username: string, password: string) {
    return this.http.post('http://localhost:8080/user/login', {username: username, password: password}, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})});
  }

  public saveUserToLocalStorage(username: string, password: string, message:Object){
    let newUser = {username: username, password: "", accessToken: JSON.parse(JSON.stringify(message)).accessToken, refreshToken: JSON.parse(JSON.stringify(message)).refreshToken};
    this.user.next(newUser);
    localStorage.setItem("user", JSON.stringify(this.user.getValue()));
    setTimeout(() => {
      this.refreshToken();
    }, 300000);
  }

  public refreshToken(){
    const user = localStorage.getItem("user");
    if (user != null) {
      let refreshToken: String = JSON.parse(user).refreshToken;
      let accessToken: String = JSON.parse(user).accessToken;
      this.http.post('http://localhost:8080/user/refresh', {refreshToken: refreshToken}, {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer ' + accessToken })})
        .subscribe({next: message => {
          setTimeout(() => {
            this.refreshToken();
          }, 300000);
          }, error: err => {
            localStorage.clear();
            this.routes.navigate(["/login"]);
          }});
    }
    else{
      localStorage.clear();
      this.routes.navigate(["/login"]);
    }


  }
}

