import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {GroupService} from "../group.service";
import {Message} from "./Message";
import {Router} from "@angular/router";

@Component({
  selector: 'app-group-discussion-page',
  templateUrl: './group-discussion-page.component.html',
  styleUrls: ['./group-discussion-page.component.css']
})
export class GroupDiscussionPageComponent implements OnInit {
  group : { id: number; } = {id:0};
  messages : Message[] = [];
  isFetching: boolean = false;
  error: boolean = false;

  constructor(private route: ActivatedRoute, private groupService: GroupService, private router: Router) { }

  ngOnInit(): void {
    this.group={
      id: this.route.snapshot.params['id']
    };
    this.route.params.subscribe(
      (params:Params)=>{
        this.group.id= params['id'];
      }
    )
    this.messages= [{
      id:1,
      user_id:1,
      group_id:1,
      title: "Ce mai incurcatura",
      text:"Sa vedeti ce am patit"
    },
      {
        id:2,
        user_id:1,
        group_id:1,
        title: "Ce mai incurcatura2",
        text:"Sa vedeti ce am patit2"
      },
      {
        id:3,
        user_id:1,
        group_id:1,
        title: "Ce mai incurcatura3",
        text:"Sa vedeti ce am patit3"
      },
    ];
    // this.groupService.getGroupDiscussions(this.group.id).subscribe(
    //   messages=>{
    //     this.isFetching=true;
    //     this.messages= messages;
    //   },error => {
    //     this.isFetching= false;
    //     this.error= true;
    //   }
    // );
    if(this.error){
      this.router.navigate(['/error']);
    }
  }

  // onGoBack(){
  //
  // }

  onSubscribe(){

  }

  onLike(id: number){
    console.log(id);
  }

  onDislike(id: number){
    console.log(id);
  }

}
