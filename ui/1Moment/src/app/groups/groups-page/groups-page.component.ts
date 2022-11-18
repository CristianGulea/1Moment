import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {GroupService} from "../group.service";
import {Group} from "./Group";
import {Router} from "@angular/router";

@Component({
  selector: 'app-groups-page',
  templateUrl: './groups-page.component.html',
  styleUrls: ['./groups-page.component.css']
})
export class GroupsPageComponent implements OnInit {

  groups : Group[] = [];
  isFetching: boolean = false;
  error: boolean = false;
  user = "Ion";
  searchTerm = '';

  constructor(private route: ActivatedRoute, private groupService: GroupService, private router: Router) { }

  ngOnInit(): void {
    // this.route.params.subscribe(
    //   (params:Params)=>{
    //     this.group.id= params['id'];
    //   }
    // )
    this.groups= [
      {
        id:1,
        name:"Group1"
      },
      {
        id:2,
        name:"Group2"
      },
      {
        id:3,
        name:"Group3"
      },
    ];

    if(this.error){
      this.router.navigate(['/error']);
    }
  }

  onLike(arg0: any) {
    throw new Error('Method not implemented.');
  }

  onLogout(){
    throw new Error('Method not implemented.');
  }

  onSubscribe(group: Group){
    this.router.navigate(['group/:id']),{
      queryParams: {id: group.id}
    }
  }

  onGoBack(){
    throw new Error('Method not implemented.');
  }

  searchGroup(searchTerm: string) {
    throw new Error('Method not implemented.');
  }
}
