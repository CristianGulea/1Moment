import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {GroupService} from "../group.service";
import {Message} from "./Message";
import {Router} from "@angular/router";
import {Group} from "../groups-page/Group";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {FormControl} from "@angular/forms";
import {LoginService} from "../../components/login/login-service";
import { ComponentType } from '@angular/cdk/portal';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-group-discussion-page',
  templateUrl: './group-discussion-page.component.html',
  styleUrls: ['./group-discussion-page.component.css']
})
export class GroupDiscussionPageComponent implements OnInit {
  group:Group={ id:0,name:''};
  messages: Message[] = [];
  isFetching: boolean = false;
  error: boolean = false;
  isSubscribed: boolean = true;
  currentUserName:string;

  constructor(public matDialog: MatDialog,
              private route: ActivatedRoute,
              private groupService: GroupService,
              private router: Router,
              private loginService: LoginService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.currentUserName= this.loginService.username;
    const id =this.route.snapshot.params['id']
    this.group.id=id;
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
      this.group = value;
      this.isSubscribed= value.subscribed? value.subscribed :false;
    })
  }

  // onGoBack(){
  //
  // }

  onSubscribe(){
    if(this.isSubscribed)
      this.groupService.unsubscribe(this.group.id).subscribe(value => {});
    else
      this.groupService.subscribe(this.group.id).subscribe(value => {});
    this.isSubscribed= !this.isSubscribed;
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

  // @ts-ignore
  openDialog(content): void {
    this.matDialog.open(content, {
      data: { messages: this.messages},
      height: '300',
      width: '600px',
    });
  }

  public dateControl = new FormControl(new Date());
  public titleControl = new FormControl();
  public contentControl = new FormControl;
  onSaveClick() {
    const date=this.dateControl.value;
    // @ts-ignore
    let hoursDiff = date?.getHours()- date?.getTimezoneOffset() /60
    date?.setHours(hoursDiff);
    // @ts-ignore
    const discussion: Message = {
      userId: this.loginService.user.value.id ? this.loginService.user.value.id : 1,
      groupId: this.group.id,
      parentMessageId: "null",
      title: this.titleControl.value,
      content: this.contentControl.value,
      publishDate: date ? date : new Date(),
      username: this.loginService.user.value.username,
      groupName: this.group.name,
      liked: false,
      likeCount: 0
    };
    this.groupService.saveADiscussion(discussion).subscribe(value => {
      this.snackBar.open("Discussion added with success", "ok", {
        duration: 2000,
      });
      value.publishDate= new Date(value.publishDate);
      if (value.publishDate <= new Date()) {
        this.messages = [...this.messages, value];
      }
    });

    this.onCancelClick();
  }

  onCancelClick() {
    this.matDialog.closeAll();
  }

  dateUpdated() {

  }

}

