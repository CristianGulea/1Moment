import { Injectable } from '@angular/core';
import {HttpClient,  HttpHeaders} from "@angular/common/http";
import { BehaviorSubject } from 'rxjs';
import {User} from "./User";
import {Router} from "@angular/router";

const TOKEN_REFRESH_TIMEOUT = 600000;

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  user = new BehaviorSubject<User>({});

  constructor(private http: HttpClient, private routes: Router) { }

  public handleLoginByCredentials(username: string, password: string) {
    return this.http.post('http://localhost:8080/user/login', {username: username, password: password}, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})});
  }

  public saveUserToLocalStorage(username: string, password: string, message:User){
    let newUser = {id: message.id, username: username, password: "", accessToken: JSON.parse(JSON.stringify(message)).accessToken, refreshToken: JSON.parse(JSON.stringify(message)).refreshToken};
    this.user.next(newUser);
    console.log(this.user);
    localStorage.setItem("user", JSON.stringify(this.user.getValue()));
    setTimeout(() => {
      this.refreshToken();
    }, TOKEN_REFRESH_TIMEOUT);
  }

  public refreshToken(){
    const user = localStorage.getItem("user");
    if (user != null) {
      let refreshToken: String = JSON.parse(user).refreshToken;
      let accessToken: String = JSON.parse(user).accessToken;
      this.http.post('http://localhost:8080/user/refresh', {refreshToken: refreshToken}, {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer ' + accessToken })})
        .subscribe({next: () => {
            setTimeout(() => {
              this.refreshToken();
            }, TOKEN_REFRESH_TIMEOUT);
          }, error: () => {
            localStorage.clear();
            this.routes.navigate(["/login"]);
          }});
    }
    else{
      localStorage.clear();
      this.routes.navigate(["/login"]);
    }


  }

  logout() {
    localStorage.clear();
    this.routes.navigate(["/login"]);
  }

  signup(user:User){
    return this.http.post('http://localhost:8080/user',
      { username: user.username, password:user.password},
      {headers: new HttpHeaders({'Content-Type': 'application/json'})}
    )
  }
}
