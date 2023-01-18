import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Message} from "./group-discussion-page/Message";
import {Group} from "./groups-page/Group";
import {LoginService} from "../components/login/login-service";
import {User} from "../components/login/User";

@Injectable({providedIn:"root"})
export class GroupService{

  user:User={};
  constructor(private http:HttpClient, private loginService: LoginService) {
    this.loginService.user.subscribe(value => this.user=value);
  }

  getGroupDiscussions(id:number){
    return this.http.get<Message[]>('http://localhost:8080/message/',{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken}),
      params: new HttpParams().set('id', id)
    });
  }

  getGroups(){
    return this.http.get<Group[]>('http://localhost:8080/group',{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken})
    });
  }

  subscribe(groupId:number){
    return this.http.patch('http://localhost:8080/group/'+groupId+'/subscribe',{},
    {
      headers: new HttpHeaders({ 'Authorization': 'Bearer '+this.user.accessToken})
    });
  }

  unsubscribe(groupId:number){
    return this.http.patch('http://localhost:8080/group/'+groupId+'/unsubscribe',{},
      {
      headers: new HttpHeaders({'Authorization': 'Bearer '+this.user.accessToken})
    });
  }

  getGroupById(id:number){
    return this.http.get<Group>('http://localhost:8080/group/'+id ,{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer '+this.user.accessToken})
    });
  }

  likeMessage(messageId: number){
    return this.http.patch('http://localhost:8080/message/'+messageId+'/like',
      {},
      {headers: new HttpHeaders({'Authorization': 'Bearer '+this.user.accessToken})}
    );
  }

  dislikeMessage(messageId: number){
    return this.http.patch('http://localhost:8080/message/'+messageId+'/dislike',{},
      {headers: new HttpHeaders({'Authorization': 'Bearer '+this.user.accessToken})}
    );
  }

  saveADiscussion(discussion: Message){
    return this.http.post<Message>('http://localhost:8080/message', discussion,
      {headers: new HttpHeaders({'Authorization': 'Bearer '+this.user.accessToken})});
  }

  createAGroup(group: Group){
    return this.http.post<Group>('http://localhost:8080/group', group,
      {headers: new HttpHeaders({'Authorization': 'Bearer '+this.user.accessToken})});
  }
}
