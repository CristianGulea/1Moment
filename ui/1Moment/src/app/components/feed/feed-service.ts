import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {LoginService} from "../login/login-service";
import {User} from "../login/User";
import {Message} from "../../groups/group-discussion-page/Message";

@Injectable({
  providedIn: 'root',
})
export class FeedService{
  user:User={};
  constructor(private http: HttpClient, private router: Router, private loginService: LoginService) {
    this.loginService.user.subscribe(value => this.user=value);
  }

  public getUsersDiscussion(){
    this.loginService.user.subscribe(value => this.user=value);
    if (this.user) {
      const url = `http://localhost:8080/message?username=${this.user.username}`;
      return this.http.get<Message[]>(url, {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken})
      });
    }
    return null;
  }

  public getPopularComments(){
    this.loginService.user.subscribe(value => this.user=value);
    if (this.user){
      const url = `http://localhost:8080/message?usernameForLikes=${this.user.username}`;
      return this.http.get<Message[]>(url, {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken})
      });
    }
    return null;
  }
}
