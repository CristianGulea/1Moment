import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {GroupService} from "../group.service";
import {Group} from "./Group";
import {Router} from "@angular/router";
import {User} from "../../components/login/User";
import {LoginService} from "../../components/login/login-service";

@Component({
  selector: 'app-groups-page',
  templateUrl: './groups-page.component.html',
  styleUrls: ['./groups-page.component.css']
})
export class GroupsPageComponent implements OnInit {

  groups : Group[] = [];
  isFetching: boolean = false;
  error: boolean = false;
  user:User={};
  searchTerm = '';

  constructor(private route: ActivatedRoute, private groupService: GroupService, private router: Router, private loginService: LoginService) { }

  ngOnInit(): void {
    this.isFetching=true;
    this.loginService.user.subscribe(value => this.user=value);
    this.groupService.getGroups().subscribe(value => {this.groups= value; this.isFetching=false}, () =>{ this.error= true; this.isFetching=false});
    if(this.error){
      this.router.navigate(['/error']);
    }
  }

  onLike(arg0: any) {
    throw new Error('Method not implemented.');
  }

  onLogout(){
    this.loginService.logout();
  }

  onSubscribe(group: Group) {
    this.router.navigate(['group/', group.id])
  }


  onGoBack(){
    throw new Error('Method not implemented.');
  }

  searchGroup(searchTerm: string) {
    throw new Error('Method not implemented.');
  }

  navigateToFeed() {
    this.router.navigate(["/feed"]);
  }
}
