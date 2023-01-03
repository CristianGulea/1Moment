import {Component, OnInit, ViewChild} from '@angular/core';
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
    this.isFetching=true;
    this.groupService.getGroupDiscussions(this.group.id).subscribe(
      messages=>{
        this.isFetching=false;
        console.log(messages);
        this.messages= messages;
      },error => {
        this.isFetching= false;
        this.error= true;
      }
    );
    if(this.error){
      this.router.navigate(['/error']);
    }
  }

  onGoBack(){
      this.router.navigate(["/groups"]);
  }

  onSubscribe(){

  }

  onLike(id: number){
    console.log(id);
  }

  onDislike(id: number){
    console.log(id);
  }

  onAddDiscussion(){}

}
