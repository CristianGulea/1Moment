import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "./DialogData";
import {GroupService} from "../group.service";
import {Message} from "./Message";
import {FormControl} from "@angular/forms";
import {LoginService} from "../../components/login/login-service";

@Component({
  selector: 'dialog-overview-dialog',
  templateUrl: 'dialog-overview.html',
})
export class Dialog{
  constructor(
    public dialogRef: MatDialogRef<Dialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private groupService: GroupService,
    private loginService: LoginService,
  ) {}
  public dateControl = new FormControl(new Date());
  public titleControl = new FormControl();
  public contentControl = new FormControl;
  onSaveClick() {
    // @ts-ignore
    const discussion: Message = {
      userId: this.loginService.user.value.id ? this.loginService.user.value.id : 1,
      groupId: this.data.group.id,
      parentMessageId: "null",
      title: this.titleControl.value,
      content: this.contentControl.value,
      publishDate: this.dateControl.value ? this.dateControl.value : new Date(),
      username: this.loginService.user.value.username,
      groupName: this.data.group.name,
      liked: false,
      likeCount: 0
    };
    this.groupService.saveADiscussion(discussion).subscribe();
    this.onCancelClick();
    if (discussion.publishDate <= new Date()) {
      this.data.messages = [...this.data.messages, discussion];
    }
    //alert(discussion.userId + " " + discussion.username + " " + discussion.content + " " + discussion.title + " " + discussion.liked + " " + discussion.publishDate + " " + discussion.parentMessageId + " " + discussion.likeCount + " " + discussion.groupName + " " + discussion.groupId);
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  dateUpdated() {

  }
}
