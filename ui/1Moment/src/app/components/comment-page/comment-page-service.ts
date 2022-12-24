import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LoginService} from "../login/login-service";
import {User} from "../login/User";
import {Message} from "../../groups/group-discussion-page/Message";

@Injectable({providedIn:"root"})
export class CommentPageService{


  user:User={};
  constructor(private http:HttpClient, private loginService: LoginService) {
    this.loginService.user.subscribe(value => this.user=value);
  }

  getDiscussionComments(parentId:number){
    return this.http.get<Message[]>('http://localhost:8080/message',{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken}),
      params: new HttpParams().set('parentId', parentId)
    });
  }

  getMessage(id:number){
    return this.http.get<Message>('http://localhost:8080/message/'+id,{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken}),
    });
  }

  addComment(message: Message) {
    return this.http.post<Message>('http://localhost:8080/message/',
      message,
      {headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken})}
  );
  }
}
