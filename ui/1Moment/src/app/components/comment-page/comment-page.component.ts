import { Component, OnInit } from '@angular/core';
import {LoginService} from "../login/login-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-comment-page',
  templateUrl: './comment-page.component.html',
  styleUrls: ['./comment-page.component.css']
})
export class CommentPageComponent implements OnInit {

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
