import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {GroupService} from "../group.service";
import {Message} from "./Message";
import {Router} from "@angular/router";
import {Group} from "../groups-page/Group";
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {Dialog} from "./dialog-page";

@Component({
  selector: 'app-group-discussion-page',
  templateUrl: './group-discussion-page.component.html',
  styleUrls: ['./group-discussion-page.component.css']
})
export class GroupDiscussionPageComponent implements OnInit {
  group: { id: number; } = {id: 0};
  messages: Message[] = [];
  isFetching: boolean = false;
  error: boolean = false;
  groupData: Group;

  constructor(public dialog: MatDialog, private route: ActivatedRoute, private groupService: GroupService, private router: Router) {
  }

  ngOnInit(): void {
    this.group = {
      id: this.route.snapshot.params['id']
    };
    this.route.params.subscribe(
      (params: Params) => {
        this.group.id = params['id'];
      }
    )
    this.isFetching = true;
    this.groupService.getGroupDiscussions(this.group.id).subscribe(
      messages => {
        this.isFetching = false;
        console.log(messages);
        this.messages = messages;
      }, error => {
        this.isFetching = false;
        this.error = true;
      }
    );
    if (this.error) {
      this.router.navigate(['/error']);
    }
    this.groupService.getGroupById(this.group.id).subscribe(value => {
      this.groupData = value;
    })
  }

  // onGoBack(){
  //
  // }

  onSubscribe() {

  }

  onLike(message: Message) {
    message.liked = !message.liked;
    this.groupService.likeMessage(message.id).subscribe();
  }

  onDislike(message: Message) {
    message.liked = !message.liked;
    this.groupService.dislikeMessage(message.id).subscribe();
  }

  onDiscutionSelected(id: number) {
    this.router.navigate(['/comments', id])
  }

  openDialog(): void {
    this.dialog.open(Dialog, {
      data: {group: this.groupData, messages: this.messages},
      height: '300',
      width: '600px',
    });
  }

}

