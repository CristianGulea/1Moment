import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Message} from "./group-discussion-page/Message";
import {Group} from "./groups-page/Group";

@Injectable({providedIn:"root"})
export class GroupService{

  constructor(private http:HttpClient) {
  }

  getGroupDiscussions(id:number){
    return this.http.get<Message[]>('',{
      headers: new HttpHeaders({'Content-Type': 'application/json'}),
      params: new HttpParams().set('id', id)
    });
  }

  getGroups(){
    return this.http.get<Group[]>('',{
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }
}
