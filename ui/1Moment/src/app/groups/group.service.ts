import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Message} from "./group-discussion-page/Message";
import {Group} from "./groups-page/Group";

@Injectable({providedIn:"root"})
export class GroupService{

  constructor(private http:HttpClient) {
  }

  getGroupDiscussions(id:number){
    return this.http.get<Message[]>('http://localhost:8080/message/',{
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiQURNSU4iLCJleHAiOjE2NjkyMjk2ODB9.lgwhS8b1TpoAuvTEv-dP1adgKSJc9WXtQgNVktlH-4U'}),
      params: new HttpParams().set('id', id)
    });
  }

  getGroups(){
    return this.http.get<Group[]>('',{
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }
}
