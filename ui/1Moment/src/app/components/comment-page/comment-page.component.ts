import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login-service";
import {ActivatedRoute, Router} from "@angular/router";
import {Message} from "../../groups/group-discussion-page/Message";
import {CommentPageService} from "./comment-page-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {MatSnackBar,MatSnackBarModule } from "@angular/material/snack-bar";

@Component({
  selector: 'app-comment-page',
  templateUrl: './comment-page.component.html',
  styleUrls: ['./comment-page.component.css']
})
export class CommentPageComponent implements OnInit {
  currentDiscussion: Message;
  comments: Message[];
  isError: boolean = false;
  isLoading: boolean = false;
  public formGroup = new FormGroup({
    date: new FormControl(null, [Validators.required]),
    date2: new FormControl(null, [Validators.required])
  })
  public dateControl = new FormControl(new Date());

  constructor(private loginService: LoginService,
              private router: Router,
              private route: ActivatedRoute,
              private commentService: CommentPageService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.commentService.getMessage(this.route.snapshot.params['id']).subscribe(value => {
      this.currentDiscussion = value;
      this.currentDiscussion.publishDate = new Date(this.currentDiscussion.publishDate);
      this.commentService.getDiscussionComments(this.route.snapshot.params['id']).subscribe(value => {
          this.comments = value.filter(value1 => value1.publishDate = new Date(value1.publishDate));
        }
        , error => {
          this.isError = true;
          this.isLoading = false
        });
      this.isLoading = false
    }, error => {
      this.isLoading = false;
      this.isError = true
    })
  }

  onLogout() {
    this.loginService.logout();
  }

  navigateDiscussionPage() {
    this.router.navigate(["/comments"]);
  }

  addComment(addCommentForm: NgForm) {
    // @ts-ignore
    const message: Message = {
      groupId: this.currentDiscussion.groupId,
      userId: this.commentService.user.id ? this.commentService.user.id : 1,
      parentMessageId: this.currentDiscussion.id?.toString(),
      content: addCommentForm.value.comment,
      publishDate: this.dateControl.value ? this.dateControl.value : new Date(),
      username: this.commentService.user.username,
      title: 'comment'
    };
    addCommentForm.resetForm();
    this.snackBar.open("Comment added with success", "ok", {
      duration: 2000,
    });
    this.commentService.addComment(message).subscribe(value => {
      value.publishDate= new Date(value.publishDate);
      if (value.publishDate <= new Date()) {
        value.username = this.commentService.user.username;
        value.publishDate = new Date(value.publishDate);
        this.comments = [...this.comments, value];
      }
    });
  }

  onLikeChange(comment: Message) {
    comment.liked = !comment.liked;
    // @ts-ignore
    comment.likeCount = comment.likeCount + (comment.liked ? +1 : -1);
    comment.liked ? this.commentService.like(comment.id).subscribe() : this.commentService.dislike(comment.id).subscribe();
  }

  dateUpdated() {
    // console.log(this.dateControl.value?.toLocaleDateString()+"T"+this.dateControl.value?.toLocaleTimeString())
  }
}
