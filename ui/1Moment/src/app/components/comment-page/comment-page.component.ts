import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login-service";
import {ActivatedRoute, Router} from "@angular/router";
import {Message} from "../../groups/group-discussion-page/Message";
import {CommentPageService} from "./comment-page-service";
import * as buffer from "buffer";
import {NgForm} from "@angular/forms";

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

  constructor(private loginService: LoginService, private router: Router, private route: ActivatedRoute, private commentService: CommentPageService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    console.log(this.route.snapshot.params['id'])
    this.commentService.getMessage(this.route.snapshot.params['id']).subscribe(value => {
      this.currentDiscussion = value;
      this.currentDiscussion.publishDate= new Date(this.currentDiscussion.publishDate);
      this.commentService.getDiscussionComments(this.route.snapshot.params['id']).subscribe(value => {
          this.comments = value.filter(value1 => value1.publishDate= new Date(value1.publishDate));
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
    const message :Message={
      groupId: this.currentDiscussion.groupId,
      userId: this.commentService.user.id?this.commentService.user.id:1,
      parentMessageId: this.currentDiscussion.id?.toString(),
      content: addCommentForm.value.comment,
      publishDate: new Date(),
    };
    this.commentService.addComment(message).subscribe(value=>console.log(value));
    console.log(addCommentForm.value.comment)
  }
}
