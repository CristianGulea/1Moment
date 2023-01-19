import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../groups/group.service";
import {LoginService} from "../login/login-service";
import {Message} from "../../groups/group-discussion-page/Message";
import {User} from "../login/User";
import {FeedService} from "./feed-service";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  discussions: Message[] = [];
  commentsMap = new Map<string, Message>();
  currentUserName:string;

  constructor(private route: ActivatedRoute, private router: Router, private feedService: FeedService, private loginService: LoginService) {
    feedService.getUsersDiscussion()?.subscribe(list => {
      if (list != null) {
        this.discussions = list.filter(value1 => value1.publishDate = new Date(value1.publishDate));
      }
    });

    feedService.getPopularComments()?.subscribe(list => {
      if (list != null){
        list.forEach(comment => {
          this.commentsMap.set(comment.parentMessageId, comment);
        })
      }
    });
    console.log(this.commentsMap);
  }

  ngOnInit(): void {
    this.currentUserName= this.loginService.username;
  }

  onLogout(){
    this.loginService.logout();
  }

  navigateGroupPage() {
    this.router.navigate(["/groups"])
  }

  getPopularComment(key: number){
    if (this.commentsMap.has(key + "")){
      return this.commentsMap.get(key + "");
    }
    return undefined;
  }

  navigateCommentPage(discussion: Message) {
    this.router.navigate(["/comments/" + discussion.id]);
  }
}
