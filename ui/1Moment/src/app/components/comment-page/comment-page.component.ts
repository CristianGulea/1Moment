import { Component, OnInit } from '@angular/core';
import {LoginService} from "../login/login-service";
import {Router} from "@angular/router";
import {Message} from "../../groups/group-discussion-page/Message";

@Component({
  selector: 'app-comment-page',
  templateUrl: './comment-page.component.html',
  styleUrls: ['./comment-page.component.css']
})
export class CommentPageComponent implements OnInit {
  currentDiscussion: Message = {id: 1, title: "Review-uri false la Altex?", userId: 1, groupId: 1, parentMessageId: "null", content:"\"Doar mie mi se par ca 90% din aceste recenzi sunt false? Toate folosesc acelasi expresii: “62000 rotati, 14 zile bateria, technolgia X”.", username:"Cristian", groupName: "General", publishDate: new Date(500000000000)};
  comments: Message[] = [{id: 1, title: "", userId: 1, groupId: 1, parentMessageId: "null", content:"Review la aparat de tuns barba cumparat recent: \"Foarte ok acest telefon. Hmmm... oare nu il folosesc eu cum trebuie? ", username:"Cristian", groupName: "General", publishDate: new Date(5000000000)}, {id: 1, title: "", userId: 1, groupId: 1, parentMessageId: "null", content:"Review la aparat de tuns barba cumparat recent: \"Foarte ok acest telefon. Hmmm... oare nu il folosesc eu cum trebuie?", username:"Cristian", groupName: "General", publishDate: new Date(500000000)}, {id: 1, title: "", userId: 1, groupId: 1, parentMessageId: "null", content:"Review la aparat de tuns barba cumparat recent: \"Foarte ok acest telefon. Hmmm... oare nu il folosesc eu cum trebuie? ", username:"Cristian", groupName: "General", publishDate: new Date(600000000000)}];

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  onLogout() {
    this.loginService.logout();
  }

  navigateDiscussionPage() {
    this.router.navigate(["/comments"]);
  }
}
