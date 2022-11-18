import { Component, OnInit } from '@angular/core';
import { LoginService } from './login-service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";

  constructor(private loginService: LoginService, private routes: Router) {}

  ngOnInit(): void {
  }

  hide: boolean = true;

  public login() {
    this.loginService.handleLoginByCredentials(this.username, this.password);
    this.routes.navigate(["/groups"]);
  }
}
