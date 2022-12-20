import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../groups/group.service";
import {LoginService} from "../login/login-service";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  constructor(private route: ActivatedRoute, private router: Router, private loginService: LoginService) { }

  ngOnInit(): void {
  }

  onLogout(){
    this.loginService.logout();
  }

}
